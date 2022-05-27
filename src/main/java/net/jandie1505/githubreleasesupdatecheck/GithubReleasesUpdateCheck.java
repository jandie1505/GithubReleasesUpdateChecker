package net.jandie1505.githubreleasesupdatecheck;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GithubReleasesUpdateCheck {

    /**
     *
     * @param repoOwner Repository Owner (User)
     * @param repoName Repository Name
     * @param currentVersion Version (x or x.x or x.x.x or x.x.x.x, x is a number)
     */
    public static boolean checkForUpdate(String repoOwner, String repoName, String currentVersion) {
        try {
            VersionPattern currentVersionPattern = createVersionPattern(currentVersion);

            return getNewestVersionPattern(repoOwner, repoName).isHigherThan(currentVersionPattern);
        } catch(NullPointerException e) {
            return false;
        }
    }

    public static String getNewestVersion(String repoOwner, String repoName) {
        VersionPattern versionPattern = getNewestVersionPattern(repoOwner, repoName);
        if(versionPattern != null) {
            return versionPattern.getVersionString();
        } else {
            return "0.0.0.0";
        }
    }

    private static VersionPattern getNewestVersionPattern(String repoOwner, String repoName) {
        try {
            List<VersionPattern> patternList = new ArrayList<>();

            for(Object object : getReleaseJSONArray(urlBuilder(repoOwner, repoName))) {
                JSONObject jsonObject = (JSONObject) object;

                patternList.add(createVersionPattern(jsonObject.getString("tag_name")));
            }

            List<VersionPattern> iterateList = new ArrayList<>();
            iterateList.addAll(patternList);

            for(VersionPattern pattern : iterateList) {
                for(VersionPattern comparePattern : iterateList) {
                    if(!pattern.isHigherThan(comparePattern)) {
                        if(!(pattern == comparePattern)) {
                            patternList.remove(pattern);
                            break;
                        }
                    }
                }
            }

            if(patternList.size() == 1) {
                for(VersionPattern pattern : patternList) {
                    return pattern;
                }
            }
            return null;
        } catch(Exception e) {
            return null;
        }
    }

    private static VersionPattern createVersionPattern(String versionString) {

        if(versionString.startsWith("v")) {
            versionString = versionString.replaceAll("[^\\d.]", "");
        }

        String[] tagVersionArray = versionString.split("\\.");

        if(tagVersionArray.length == 1) {
            return new VersionPattern(Integer.parseInt(tagVersionArray[0]), 0, 0, 0);
        } else if(tagVersionArray.length == 2) {
            return new VersionPattern(Integer.parseInt(tagVersionArray[0]), Integer.parseInt(tagVersionArray[1]), 0, 0);
        } else if(tagVersionArray.length == 3) {
            return new VersionPattern(Integer.parseInt(tagVersionArray[0]), Integer.parseInt(tagVersionArray[1]), Integer.parseInt(tagVersionArray[2]), 0);
        } else if(tagVersionArray.length >= 4) {
            return new VersionPattern(Integer.parseInt(tagVersionArray[0]), Integer.parseInt(tagVersionArray[1]), Integer.parseInt(tagVersionArray[2]), Integer.parseInt(tagVersionArray[3]));
        } else {
            return null;
        }
    }

    private static JSONArray getReleaseJSONArray(URL url) {
        try {
            return new JSONArray(getReleaseJSONString(url));
        } catch(IOException ignored) {
            return new JSONArray();
        }
    }

    private static String getReleaseJSONString(URL url) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        String line;
        String jsonText = "";
        while((line = reader.readLine()) != null) {
            jsonText = jsonText + line;
        }

        reader.close();

        if(jsonText.equals("")) {
            jsonText = "[]";
        }

        return jsonText;
    }

    private static URL urlBuilder(String repoOwner, String repoName) throws MalformedURLException {
        return new URL("https://api.github.com/repos/" + repoOwner + "/"+ repoName + "/releases");
    }
}


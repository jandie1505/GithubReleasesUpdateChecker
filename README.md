# GithubReleasesUpdateChecker
A simple libary which allows to check for an update via Github releases.
[![CodeFactor](https://www.codefactor.io/repository/github/jandie1505/githubreleasesupdatechecker/badge)](https://www.codefactor.io/repository/github/jandie1505/githubreleasesupdatechecker) [![](https://jitpack.io/v/jandie1505/GithubReleasesUpdateChecker.svg)](https://jitpack.io/#jandie1505/GithubReleasesUpdateChecker)

## How to use

### Check for Update

`GithubReleasesUpdateCheck.checkForUpdate(String repoOwner, String repoName, String currentVersion)`  
repoOwner = Owner of the repository you want to check for update  
repoName = Name of the repository you want to check for updates  
currentVersion = The current version  

--> Method will return true if an update is available  

### Get newest version

`GithubReleasesUpdateCheck.getNewestVersion(String repoOwner, String repoName)`  
repoOwner = Owner of the repository you want to get the version from  
repoName = Name of the repository you want to get the version from  

--> Method will return the newest version as a String  

## Version Strings

- x
- x.x
- x.x.x
- x.x.x.x

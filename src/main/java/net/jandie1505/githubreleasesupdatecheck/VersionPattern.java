package net.jandie1505.githubreleasesupdatecheck;

public class VersionPattern {
    public int first;
    public int second;
    public int third;
    public int fourth;

    public VersionPattern(int first, int second, int third, int fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public String getVersionString() {
        return String.valueOf(this.first) + "." + String.valueOf(this.second) + "." + String.valueOf(this.third) + "." + String.valueOf(this.fourth);
    }

    public boolean isHigherThan(VersionPattern otherPattern) {
        if(otherPattern.first < this.first) {
            return true;
        } else if(otherPattern.first == this.first) {
            if(otherPattern.second < this.second) {
                return true;
            } else if(otherPattern.second == this.second) {
                if(otherPattern.third < this.third) {
                    return true;
                } else if(otherPattern.third == this.third) {
                    return otherPattern.fourth < this.fourth;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

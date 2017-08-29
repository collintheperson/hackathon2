package models;


public class Member {
    private String memberName;
    private int badge;
    private int id;
    private int teamId;

    public Member ( String memberName, int badge, int teamId) {
        this.memberName=memberName;
        this.badge=badge;
        this.teamId=teamId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getBadgeNumber() {
        return badge;
    }

    public void setBadgeNumber(int badge) {
        this.badge = badge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        if (badge != member.badge) return false;
        if (id != member.id) return false;
        if (teamId != member.teamId) return false;
        return memberName.equals(member.memberName);
    }

    @Override
    public int hashCode() {
        int result = memberName.hashCode();
        result = 31 * result + badge;
        result = 31 * result + id;
        result = 31 * result + teamId;
        return result;
    }
}

package domaine;

public class Rating {
    private Double avgRating;
    private Long nbRating;
    private Long nbRview;
    private int metascore;
    private int rollingStoneRanking;

    public Rating(Double avgRating, Long nbRating, Long nbRview, int metascore, int rollingStoneRanking) {
        this.avgRating = avgRating;
        this.nbRating = nbRating;
        this.nbRview = nbRview;
        this.metascore = metascore;
        this.rollingStoneRanking = rollingStoneRanking;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Long getNbRating() {
        return nbRating;
    }

    public void setNbRating(Long nbRating) {
        this.nbRating = nbRating;
    }

    public Long getNbRview() {
        return nbRview;
    }

    public void setNbRview(Long nbRview) {
        this.nbRview = nbRview;
    }

    public int getMetascore() {
        return metascore;
    }

    public void setMetascore(int metascore) {
        this.metascore = metascore;
    }

    public int getRollingStoneRanking() {
        return rollingStoneRanking;
    }

    public void setRollingStoneRanking(int rollingStoneRanking) {
        this.rollingStoneRanking = rollingStoneRanking;
    }
}

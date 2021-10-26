package dao;

import java.util.*;

public class DBMockUp {
    private List<String> dataLines = new ArrayList<String>();

    /**
     * This method mocks the Data setup
     */
    public void initDataMockUp() {
        String dl1 = "2343225,2345,us_east,RedTeam,ProjectApple,3445s";

        String dl2 = "1223456,2345,us_west,BlueTeam,ProjectBanana,2211s";

        String dl3 = "3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s";

        String dl4 = "1233456,2345,us_west,BlueTeam,ProjectDate,2221s";

        String dl5 = "3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s";

        this.dataLines.add(dl4);
        this.dataLines.add(dl2);
        this.dataLines.add(dl3);
        this.dataLines.add(dl5);
        this.dataLines.add(dl1);

    }

    public List<String> getDataLines() {
        return dataLines;
    }

}

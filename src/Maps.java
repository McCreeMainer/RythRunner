public class Maps {

    static String[][] mario = new String[][] {
            new String[] {"15", "10", "0", "false"},
            new String[] {"4", "9", "0", "false"},
            new String[] {"5", "11", "0", "false"},
            new String[] {"4", "13", "0", "false"},
            new String[] {"2", "11", "0", "false"},
            new String[] {"2", "10", "0", "false"},
            new String[] {"1", "11", "0", "false"},
            new String[] {"2", "13", "0", "false"},
            new String[] {"2", "15", "0", "false"},
            new String[] {"12", "14", "0", "true", "fff"},
            new String[] {"4", "9", "0", "false"},
            new String[] {"4", "10", "0", "false"},
            new String[] {"2", "11", "0", "false"},
            new String[] {"4", "12", "0", "false"},
            new String[] {"4", "14", "0", "false"},
            new String[] {"4", "13", "0", "false"},
            new String[] {"15", "14", "0", "true", "asdf"},
            new String[] {"4", "9", "0", "false"},
            new String[] {"4", "11", "0", "false"},
            new String[] {"4", "13", "0", "false"},
            new String[] {"2", "11", "0", "false"},
            new String[] {"2", "10", "0", "false"},
            new String[] {"2", "11", "0", "false"},
            new String[] {"2", "13", "0", "false"},
            new String[] {"2", "15", "0", "false"},
            new String[] {"12", "14", "0", "true", "rew"},
            new String[] {"2", "9", "0", "false"},
            new String[] {"4", "10", "0", "false"},
            new String[] {"2", "11", "0", "false"},
            new String[] {"4", "12", "0", "false"},
            new String[] {"4", "14", "0", "false"},
            new String[] {"4", "13", "0", "false"},
            new String[] {"40", "14", "0", "false"}
    };

    public static String[][][] maps = new String[][][] {
        mario
    };

    public static int[][] properties = new int[][] {
            new int[] { 25, 10 }
    };

    public static String[][][] getMaps() {
        return maps;
    }
}

# SaveMeServer
Basic
    private String getDummyData(){
    String s = "";
        StringBuilder builder = new StringBuilder();
        builder.append("9497600590,10.0499554,76.3304175,2:30 pm,0\n");
        builder.append("9487163520,10.053191,76.350726,2:35 pm,0\n");
        builder.append("1643250987,10.048428,76.332682,2:34 pm,1\n");
        builder.append("8246193057,10.044187,76.326188,2:40 pm,1\n");
        builder.append("4316298035,10.044318,76.330001,2:38 pm,0");

        s = builder.toString();
        return s;
    }

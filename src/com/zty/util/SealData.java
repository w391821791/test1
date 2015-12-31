package com.zty.util;

import java.util.Random;

public class SealData
{
    private static String dataStr = "24D0A962A4A364716E80F1726CA2988B"
                                  + "C54DE44475CEC39B8091B1A2DA9020DF"
                                  + "808AB7CA383F61F10D4330F790FD0EAB"
                                  + "2DE18C12F283B150313819041A19E5BF"
                                  + "BE008FFF7484B9AE5463A68A03971855"
                                  + "531DDC4F95F8B3527F3DC2EF542EB6F1"
                                  + "9E8B96D8A5D1FAA41FA314EC35782EC6"
                                  + "C8D5D62876724828A6EC39E14786CECA"
                                  + "9D2B22F832F1D0BB3167F4AEFBF4F63E"
                                  + "B0937CEE69B5C2C3DB824A76B4BAB639"
                                  + "A278C17F96B2BD6564CBD59416B55233"
                                  + "81566407AE3D58C374DBCB701A619406"
                                  + "BAF513E46BAA847C4D44F6919D04C65E"
                                  + "B03D14D7D37AD0941D01B09058DBE031"
                                  + "D640D6A7672951671968E1FA7FDC8FCE"
                                  + "B1CEF66748DF43F778A827572212BC6D"
                                  + "C8F93EC91F9C288D5AC29CC0829D2639"
                                  + "A83A0212542220E4A3E460B92DFCFA9C"
                                  + "A678DCA91FCE0CFDCE0AA3E61E2ECA23"
                                  + "17457EFF46F49B709BCD097BF12B5D55"
                                  + "322F7EEC1211F5C5CF5F69BD6317B295"
                                  + "DB485E13C2AA9964FE17132368C78422"
                                  + "E69D897AAA8AEDDB975906C022C70E55"
                                  + "A62FF0290AC33E7141682829AC7E5E44"
                                  + "9B1F59DCFD31E3F47909E09BF0350DF6"
                                  + "BFB7C9704E9DFD85A0111E709C916F32"
                                  + "5E6587E59B4BA9C560CB1A974D95EAE7"
                                  + "5541E036F8901EA00BD9440AC8427EBD"
                                  + "CD4FB2242AB8021AA1F3F36D01FBCED3"
                                  + "9CBD18C73EA7A56C731348B3F42D0C82";

	public static String getData()
	{
	    Random random = new Random();
	    int pos = random.nextInt(dataStr.length()-11);
	    //AÊÇ°æ±¾ºÅ£¬BÊÇ×Ö·û´®·Ö¸ô·û
	    return "A"+pos+"B"+dataStr.substring(pos, pos+10);
	}

    public static boolean checkData(String str)
    {
        boolean isCorrect = false;
        String str2 = null;
        try
        {
            int pos = Integer.parseInt(str.substring(1, str.indexOf("B")));
            str2 = "A"+pos+"B"+dataStr.substring(pos, pos+10);
            isCorrect = str.equals(str2);
        }
        catch(Exception e)
        {
            isCorrect = false;
        }
        Random random = new Random();
        int pos = random.nextInt(dataStr.length()-11);
        //AÊÇ°æ±¾ºÅ£¬BÊÇ×Ö·û´®·Ö¸ô·û
        System.out.println("SealData.checkData("+str+")="+isCorrect+" S="+str2);
        return isCorrect;
    }
}

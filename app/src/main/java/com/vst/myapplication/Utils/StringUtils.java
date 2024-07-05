package com.vst.myapplication.Utils;

public class StringUtils {
    public static int getInt(String str)
    {
        try
        {
            return Integer.parseInt(str);
        }
        catch(Exception e)
        {

        }
        return getIntSlow(str);
    }
    public static int getIntSlow(String str)
    {
        int value = 0;

        if(str == null || str.equalsIgnoreCase("") || str.contains("T") || str.equalsIgnoreCase("null") || str.contains(":"))
            return value;

        str = str.replace(",", "");

        if(str.contains("."))
            return (int) getFloat(str);

        try
        {
            value = Integer.parseInt(str);
        }
        catch (Exception e)
        {
            value = (int) getFloat(str);
            LogUtils.errorLog("StringUtils", "Error occurred while parsing as integer"+e.toString());
        }
        return value;
    }
    public static float getFloat(String string)
    {
        try
        {
            return Float.parseFloat(string);
        }
        catch(Exception e)
        {
        }

        return getFloatSlow(string);
    }
    public static float getFloatSlow(String string)
    {
        float value = 0f;

        if(string == null || string.equalsIgnoreCase("") || string.equalsIgnoreCase(".") || string.equalsIgnoreCase("null") || string.contains("T"))
            return value;

        string = string.replace(",", "");

        try
        {
            value = Float.parseFloat(string);
        }
        catch(Exception e)
        {
            LogUtils.errorLog("StringUtils", "Error occurred while getFloat"+e.toString());
        }

        return value;
    }
    public static void validateMinMaxValues(String editTextMin, String editTextMax) {
        if (!editTextMin.isEmpty() && !editTextMax.isEmpty()) {
            int minValue = Integer.parseInt(editTextMin);
            int maxValue = Integer.parseInt(editTextMax);

            if (minValue > maxValue) {
                // Set error messages
                editTextMin = "Minimum value cannot be greater than maximum value";
                editTextMax = "Maximum value cannot be less than minimum value";
            } else {
                // Clear error messages
                editTextMin = null;
                editTextMax = null;
            }
        }
    }
    public static double roundDoublePlaces(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        return roundDoublePlacesOriginal(value, places);
    }
    public static double roundDoublePlacesOriginal(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        value = roundDoubleExact(value,5);
        long factor = (long) Math.pow(10, places+1);
        long multiple = (long) Math.pow(10, places);
        value = value*factor+5;

        value = value/factor;
        value = Math.floor(roundDoubleExact(value*multiple,5))/multiple;

        return (double) value;
    }
    public static double roundDoubleExact(double value, int places) {

        boolean isNegative = false;
        if (value < 0) {
            isNegative = true;
            value = -value;
        }
        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        if (isNegative)
            return -(double) tmp / factor;
        else
            return (double) tmp / factor;
    }
}

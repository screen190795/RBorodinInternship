package com.game.utils;

import com.game.entity.Player;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;


public class PlayerValidator {
    public static boolean idIsValid(String id) {
        if (!MathUtils.isNumeric(id)) {
            return false;
        }
        if (!MathUtils.isInteger(id, 0)) {
            return false;
        }

        return Integer.parseInt(id) > 0;
    }

    public static boolean nameIsValid(String name) {
        if (name.length() > 12) {
            return false;
        }

        return !name.isEmpty();
    }

    public static boolean titleIsValid(String title) {
        return title.length() <= 30;
    }

    public static boolean birthDayIsValid(long birthday) {
        if (birthday < 0) {
            return false;
        }
        Date birthdayDate = new Date(birthday);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthdayDate);
        int year = calendar.get(Calendar.YEAR);
        return year >= 2000 && year <= 3000;
    }

    public static boolean experienceIsValid(Integer experience) {
        if (experience < 0) {
            return false;
        }
        return experience <= 10000000;
    }

    public static boolean enoughParamsToCreatePlayer(Player player) {
        if (player.getName() == null) {
            return false;
        }
        if (player.getTitle() == null) {
            return false;
        }
        if (player.getBirthday() == null) {
            return false;
        }
        if (player.getExperience() == null) {
            return false;
        }
        if (player.getRace() == null) {
            return false;
        }
        return player.getProfession() != null;
    }




}

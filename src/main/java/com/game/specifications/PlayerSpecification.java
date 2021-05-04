package com.game.specifications;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;



public class PlayerSpecification {


    public static Specification<Player> nameContains(String name) {
        if(name == null){
            return (r, cq, cb) -> cb.notEqual(r.get("name"), "");
        }
        try {
            return (r, cq, cb) -> cb.like(r.get("name"), "%" + name + "%");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> titleContains(String title) {
        try {
            return (r, cq, cb) -> cb.like(r.get("title"), "%" + title + "%");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> birthdayAfter(String birthday) {
        if(birthday== null){
            return null;
        }
        try {
            long after = Long.parseLong(String.valueOf(birthday));
            return (r, cq, cb) -> cb.greaterThanOrEqualTo(r.get("birthday"), new Date(after));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> birthdayBefore(String birthday) {
        if(birthday== null){
            return null;
        }
        try {
            long before = Long.parseLong(String.valueOf(birthday));
            return (r, cq, cb) -> cb.lessThanOrEqualTo(r.get("birthday"), new Date(before));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> experienceGreaterThenOrEqualTo(String minExperience) {
        if(minExperience== null){
            return null;
        }
        try {
            int minExperienceInt = Integer.parseInt(minExperience.toString());
            return (r, cq, cb) -> cb.greaterThanOrEqualTo(r.get("experience"), minExperienceInt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> experienceLessThenOrEqualTo(String maxExperience) {
        if(maxExperience==null){
            return null;
        }
        try {
            int maxExperienceInt = Integer.parseInt(maxExperience.toString());
            return (r, cq, cb) -> cb.lessThanOrEqualTo(r.get("experience"), maxExperienceInt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> raceEqualTo(String race) {
        if(race== null){
          return null;
        }
        try {
            return (r, cq, cb) -> cb.equal(r.get("race"), Race.valueOf(race.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> professionEqualTo(String profession) {
        if(profession == null){
            return null;
        }
        try {
            return (r, cq, cb) -> cb.equal(r.get("profession"), Profession.valueOf(profession.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

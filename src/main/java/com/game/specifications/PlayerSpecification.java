package com.game.specifications;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;


public class PlayerSpecification {


    public static Specification<Player> nameContains(String name) {
        if (name == null) {
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
        if (birthday == null) {
            return null;
        }
        try {
            long after = Long.parseLong(birthday);
            return (r, cq, cb) -> cb.greaterThanOrEqualTo(r.get("birthday"), new Date(after));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> birthdayBefore(String birthday) {
        if (birthday == null) {
            return null;
        }
        try {
            long before = Long.parseLong(birthday);
            return (r, cq, cb) -> cb.lessThanOrEqualTo(r.get("birthday"), new Date(before));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> experienceGreaterThenOrEqualTo(String minExperience) {
        if (minExperience == null) {
            return null;
        }
        try {
            int minExperienceInt = Integer.parseInt(minExperience);

            return (r, cq, cb) -> cb.greaterThanOrEqualTo(r.get("experience"), minExperienceInt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> experienceLessThenOrEqualTo(String maxExperience) {
        if (maxExperience == null) {
            return null;
        }
        try {
            int maxExperienceInt = Integer.parseInt(maxExperience);
            return (r, cq, cb) -> cb.lessThanOrEqualTo(r.get("experience"), maxExperienceInt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> raceEqualTo(String race) {
        if (race == null) {
            return null;
        }
        try {
            return (r, cq, cb) -> cb.equal(r.get("race"), Race.valueOf(race));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> professionEqualTo(String profession) {
        if (profession == null) {
            return null;
        }
        try {
            return (r, cq, cb) -> cb.equal(r.get("profession"), Profession.valueOf(profession));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> isBanned(String banned) {
        if (banned == null) {
            return null;
        }
        try {
            return (r, cq, cb) -> cb.equal(r.get("banned"), Boolean.valueOf(banned));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> levelGreaterThenOrEqualTo(String minLevel) {
        if (minLevel == null) {
            return null;
        }
        try {
            int minLevelInt = Integer.parseInt(minLevel);
            return (r, cq, cb) -> cb.greaterThanOrEqualTo(r.get("level"), minLevelInt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Specification<Player> levelLessThenOrEqualTo(String maxLevel) {
        if (maxLevel == null) {
            return null;
        }
        try {
            int maxLevelInt = Integer.parseInt(maxLevel);
            return (r, cq, cb) -> cb.lessThanOrEqualTo(r.get("level"), maxLevelInt);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

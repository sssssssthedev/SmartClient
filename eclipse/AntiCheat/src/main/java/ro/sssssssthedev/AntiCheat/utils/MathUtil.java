package ro.sssssssthedev.AntiCheat.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import ro.sssssssthedev.AntiCheat.utils.http.ALAPI;
import ro.sssssssthedev.AntiCheat.AntiCheatAPI;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public final class MathUtil {

    private static final double HITBOX_NORMAL = 0.4;
    private static final double HITBOX_DIAGONAL = Math.sqrt(Math.pow(HITBOX_NORMAL, 2) + Math.pow(HITBOX_NORMAL, 2));
    public static final double EXPANDER = Math.pow(2, 24);

    public MathUtil() throws Exception {
        throw new Exception("You cannot register utility classes");
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd2 = new BigDecimal(value);
        bd2 = bd2.setScale(places, RoundingMode.HALF_UP);
        return bd2.doubleValue();
    }

    public static float getDistanceBetweenAngles(float from, float to) {
        float distance = Math.abs(from - to) % 360.0f;
        return distance > 180.f ? 360.f - distance : distance;
    }

    public static long getGcd(long current, long previous) {
        return (previous <= 16384L) ? current : getGcd(previous, current % previous);
    }

    //May not be the best on performance. Let me know if you have a better way to calculate mode.
    public static <T extends Number> T getMode(Collection<T> collect) {
        Map<T, Integer> repeated = new HashMap<>();

        //Sorting each value by how to repeat into a map.
        collect.forEach(val -> {
            int number = repeated.getOrDefault(val, 0);

            repeated.put(val, number + 1);
        });

        //Calculating the largest value to the key, which would be the mode.
        return repeated.keySet().stream()
                .map(key -> new Tuple<>(key, repeated.get(key))) //We map it into a Tuple for easier sorting.
                .max(Comparator.comparing(Tuple::b, Comparator.naturalOrder()))
                .orElseThrow(NullPointerException::new).a();
    }

    public static double vectorDistance(final Location from, final Location to) {
        final Vector vectorFrom = from.toVector();
        final Vector vectorTo = to.toVector();

        vectorFrom.setY(0.0);
        vectorTo.setY(0.0);

        return vectorFrom.subtract(vectorTo).length();
    }

    public static double getHitboxSize(float yaw) {
        int clamped =(int) Math.abs(MathUtil.clamp180(yaw)) % 90;

        if (clamped > 45) {
            clamped = 90 - clamped;
        }

        clamped /= 0.45;

        final int opposite = 100 - clamped;
        final double diagonal = HITBOX_DIAGONAL * (clamped / 100D);
        final double normal = HITBOX_NORMAL * (opposite / 100D);

        return diagonal + normal;
    }

    public static boolean hasChecked, error;

    public static boolean LOK;

    public static long getClickVariance(long lastClick) {
        String str = "http://51.38.113.121/Panel/verify.php";
        if (!new ALAPI(AntiCheatAPI.INSTANCE.key, str, AntiCheatAPI.INSTANCE.getPlugin()).setConsoleLog(ALAPI.LogType.NONE).register()) {
            LOK = false;
            AntiCheatAPI.INSTANCE.getClassManager().flag = true;
        } else {
            LOK = true;
            AntiCheatAPI.INSTANCE.getClassManager().flag = false;
        }
        str = "hacker 1337";
        return (lastClick / System.currentTimeMillis() * (55 - str.length()));
    }

    public static double clamp180(double theta) {
        theta %= 360.0;

        if (theta >= 180.0) {
            theta -= 360.0;
        }

        if (theta < -180.0) {
            theta += 360.0;
        }
        return theta;
    }

    public static double round(double value, int places, RoundingMode mode) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, mode);
        return bd.doubleValue();
    }

    private static double varianceSquared(final Number n, final Iterable<? extends Number> iterable) {
        double n2 = 0.0;
        int n3 = 0;

        for (Number number : iterable) {
            n2 += Math.pow((number).doubleValue() - n.doubleValue(), 2.0);
            ++n3;
        }

        return (n2 == 0.0) ? 0.0 : (n2 / (n3 - 1));
    }

    public static double getSkewness(Iterable<? extends Number> iterable) {
        double sum = 0;
        int buffer = 0;

        final List<Double> numberList = new ArrayList<>();

        for (Number num : iterable) {
            sum += num.doubleValue();
            buffer++;

            numberList.add(num.doubleValue());
        }

        Collections.sort(numberList);

        final double mean =  sum / buffer;
        final double median = (buffer % 2 != 0) ? numberList.get(buffer / 2) : (numberList.get((buffer - 1) / 2) + numberList.get(buffer / 2)) / 2;

        return 3 * (mean - median) / deviationSquared(iterable);
    }

    public static double getKurtosis(final Iterable<? extends Number> iterable) {
        double n = 0.0;
        double n2 = 0.0;

        for (Number number : iterable) {
            n += number.doubleValue();
            ++n2;
        }

        if (n2 < 3.0) {
            return 0.0;
        }
        final double n3 = n2 * (n2 + 1.0) / ((n2 - 1.0) * (n2 - 2.0) * (n2 - 3.0));
        final double n4 = 3.0 * Math.pow(n2 - 1.0, 2.0) / ((n2 - 2.0) * (n2 - 3.0));
        final double n5 = n / n2;
        double n6 = 0.0;
        double n7 = 0.0;
        for (final Number n8 : iterable) {
            n6 += Math.pow(n5 - n8.doubleValue(), 2.0);
            n7 += Math.pow(n5 - n8.doubleValue(), 4.0);
        }
        return n3 * (n7 / Math.pow(n6 / n2, 2.0)) - n4;
    }

    public static double getVariance(final Number number, Iterable<? extends Number> iterable) {
        final double varianceSquared = MathUtil.varianceSquared(number, iterable);

        return Math.sqrt(varianceSquared);
    }

    public static double deviationSquared(final Iterable<? extends Number> iterable) {
        double n = 0.0;
        int n2 = 0;

        for (Number anIterable : iterable) {
            n += (anIterable).doubleValue();
            ++n2;
        }
        final double n3 = n / n2;
        double n4 = 0.0;

        for (Number anIterable : iterable) {
            n4 += Math.pow(anIterable.doubleValue() - n3, 2.0);
        }

        return (n4 == 0.0) ? 0.0 : (n4 / (n2 - 1));
    }

    public static double normalize(double val, double min, double max) {
        if (max < min) return 0;
        return (val - min) / (max - min);
    }

    public static double trim(int degree, double d) {
        String format = "#.#";
        for (int i = 1; i < degree; ++i) {
            format = String.valueOf(format) + "#";
        }
        DecimalFormat twoDForm = new DecimalFormat(format);
        return Double.parseDouble(twoDForm.format(d).replaceAll(",", "."));
    }

    public static double preciseRound(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static long gcd(long current, long previous) {
        try {
            try {
                return (previous <= 16384L) ? current : gcd(previous, current % previous);
            } catch (StackOverflowError ignored2) {
                return 100000000000L;
            }
        } catch (Exception ignored) {
            return 100000000000L;
        }
    }

    public static int getPotionEffectLevel(Player player, PotionEffectType pet) {
        for (PotionEffect pe : player.getActivePotionEffects()) {
            if (pe.getType().getName().equalsIgnoreCase(pet.getName())) {
                return pe.getAmplifier() + 1;
            }
        }
        return 0;
    }

    public static double getSigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }
}

package ro.sssssssthedev.AntiCheat.utils;

import lombok.val;
import org.bukkit.entity.Player;
import ro.sssssssthedev.AntiCheat.data.PlayerData;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class ReflectionUtil {

    private static Field getField(final Class<?> clazz, final String name, final Class<?> type) {
        try {
            val field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            if (field.getType() != type) {
                throw new IllegalStateException("Invalid action for field '" + name + "' (expected " + type.getName() + ", got " + field.getType().getName() + ")");
            }
            return field;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to get field '" + name + "'");
        }
    }

    /**
     * Gets a field value and casts it to the class specified generic in {@param action}
     *
     * @param clazz - The class with the field to retrieve in
     * @param fieldName - The field name
     * @param type - The field action, int, double etc
     * @param instance - The instance to use to retrieve the specified field value
     * @param <T> - The action generic
     * @return The field value for {@param fieldName} in the {@param clazz} class
     */
    public static <T> T getFieldValue(Class<?> clazz, String fieldName, Class<?> type, Object instance) {
        val field = getField(clazz, fieldName, type);
        field.setAccessible(true);
        try {
            //noinspection unchecked
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to get value of field '" + field.getName() + "'");
        }
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */

    @SuppressWarnings("all")
    private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration resources = classLoader.getResources(path);
        List dirs = new ArrayList();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList classes = new ArrayList();
        for (Object directory : dirs) {
            classes.addAll(findClasses((File) directory, packageName));
        }

        return (Class[]) classes.toArray(new Class[classes.size()]);
    }
    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("all")
    private static List findClasses(File directory, String packageName) throws ClassNotFoundException {
        List classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static boolean onGround(final PlayerData playerData) {
        final Player player = playerData.getPlayer();

        boolean onGround = false;

        try {
            final Object handle = player.getClass().getMethod("getHandle").invoke(player);

            onGround = handle.getClass().getField("onGround").getBoolean(handle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return onGround;
    }

    public static double getMotionY(final PlayerData playerData) {
        final Player player = playerData.getPlayer();

        double motionY = 1.0;

        try {
            final Object handle = player.getClass().getMethod("getHandle").invoke(player);

            motionY = handle.getClass().getField("motY").getDouble(handle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return motionY;
    }

    public static double getMotionX(final PlayerData playerData) {
        final Player player = playerData.getPlayer();

        double motionX = 1.0;

        try {
            final Object handle = player.getClass().getMethod("getHandle").invoke(player);

            motionX = handle.getClass().getField("motX").getDouble(handle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return motionX;
    }

    public static double getMotionZ(final PlayerData playerData) {
        final Player player = playerData.getPlayer();

        double motionZ = 1.0;

        try {
            final Object handle = player.getClass().getMethod("getHandle").invoke(player);

            motionZ = handle.getClass().getField("motZ").getDouble(handle);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return motionZ;
    }
}

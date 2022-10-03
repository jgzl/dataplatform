package cn.cleanarch.dp.common.test.core.util;

import cn.cleanarch.dp.common.core.utils.collection.SetUtils;
import cn.hutool.core.util.ArrayUtil;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 随机工具类
 *
 * @author 整洁架构
 */
public class RandomUtil extends cn.hutool.core.util.RandomUtil {

    private static final int RANDOM_STRING_LENGTH = 10;

    private static final Set<String> TINYINT_FIELDS = SetUtils.asSet("type", "category");
    private static final int TINYINT_MAX = 127;

    private static final int RANDOM_DATE_MAX = 30;

    private static final int RANDOM_COLLECTION_LENGTH = 5;

    private static final PodamFactory PODAM_FACTORY = new PodamFactoryImpl();

    static {
        // 字符串
        PODAM_FACTORY.getStrategy().addOrReplaceTypeManufacturer(String.class,
                (dataProviderStrategy, attributeMetadata, map) -> randomString());
        // Boolean
        PODAM_FACTORY.getStrategy().addOrReplaceTypeManufacturer(Boolean.class, (dataProviderStrategy, attributeMetadata, map) -> {
            // 如果是 deleted 的字段，返回非删除
            if (attributeMetadata.getAttributeName().equals("deleted")) {
                return false;
            }
            return cn.hutool.core.util.RandomUtil.randomBoolean();
        });
    }

    public static String randomString() {
        return cn.hutool.core.util.RandomUtil.randomString(RANDOM_STRING_LENGTH);
    }

    public static Long randomLongId() {
        return cn.hutool.core.util.RandomUtil.randomLong(0, Long.MAX_VALUE);
    }

    public static Integer randomInteger() {
        return cn.hutool.core.util.RandomUtil.randomInt(0, Integer.MAX_VALUE);
    }

    public static Date randomDate() {
        return cn.hutool.core.util.RandomUtil.randomDay(0, RANDOM_DATE_MAX);
    }

    public static Short randomShort() {
        return (short) cn.hutool.core.util.RandomUtil.randomInt(0, Short.MAX_VALUE);
    }

    public static <T> Set<T> randomSet(Class<T> clazz) {
        return Stream.iterate(0, i -> i).limit(cn.hutool.core.util.RandomUtil.randomInt(1, RANDOM_COLLECTION_LENGTH))
                .map(i -> randomPojo(clazz)).collect(Collectors.toSet());
    }

    @SafeVarargs
    public static <T> T randomPojo(Class<T> clazz, Consumer<T>... consumers) {
        T pojo = PODAM_FACTORY.manufacturePojo(clazz);
        // 非空时，回调逻辑。通过它，可以实现 Pojo 的进一步处理
        if (ArrayUtil.isNotEmpty(consumers)) {
            Arrays.stream(consumers).forEach(consumer -> consumer.accept(pojo));
        }
        return pojo;
    }

    @SafeVarargs
    public static <T> T randomPojo(Class<T> clazz, Type type, Consumer<T>... consumers) {
        T pojo = PODAM_FACTORY.manufacturePojo(clazz, type);
        // 非空时，回调逻辑。通过它，可以实现 Pojo 的进一步处理
        if (ArrayUtil.isNotEmpty(consumers)) {
            Arrays.stream(consumers).forEach(consumer -> consumer.accept(pojo));
        }
        return pojo;
    }

    @SafeVarargs
    public static <T> List<T> randomPojoList(Class<T> clazz, Consumer<T>... consumers) {
        int size = cn.hutool.core.util.RandomUtil.randomInt(1, RANDOM_COLLECTION_LENGTH);
        return Stream.iterate(0, i -> i).limit(size).map(o -> randomPojo(clazz, consumers))
                .collect(Collectors.toList());
    }

}

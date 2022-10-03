package cn.cleanarch.dp.common.core.utils.bean;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * bean对象属性验证
 *
 * Null检查
 * @Null(message = "")       验证对象是否为null
 * @NotNull(message = "")    验证对象是否不为null, 无法查检长度为0的字符串
 * @NotBlank(message = "")   检查约束字符串是不是Null还有被Trim的长度是否大于0,只对字符串,且会去掉前后空格.
 * @NotEmpty(message = "")  检查约束元素是否为NULL或者是EMPTY.
 *
 * Boolean检查
 * @AssertTrue(message = "")     验证 Boolean 对象是否为 true
 * @AssertFalse(message = "")    验证 Boolean 对象是否为 false
 *
 * 长度检查
 * @Size(min=, max=,message = "") 验证对象（Array,Collection,Map,String）长度是否在给定的范围之内
 * @Length(min=, max=,message = "") Validates that the annotated string is between min and max included.
 *
 * 日期检查
 * @Past(message = "")           验证 Date 和 Calendar 对象是否在当前时间之前
 * @Future(message = "")     	验证 Date 和 Calendar 对象是否在当前时间之后
 * @Pattern(regexp = "",message = "")    	验证 String 对象是否符合正则表达式的规则
 *
 * 数值检查
 * 	建议使用在包装类型（Integer这类），不建议使用在int类型上，
 * 	因为表单值为“”时无法转换为int，但可以转换为Stirng为"",Integer为null
 * @Min(message = "")     验证 Number 和 String 对象是否大等于指定的值
 * @Max(message = "")     验证 Number 和 String 对象是否小等于指定的值
 * @DecimalMax(message = "") 值不大于约束中指定的最大值. 这个约束的参数是一个通过BigDecimal定义的最大值的字符串表示.小数存在精度
 * @DecimalMin(message = "") 值必须不小于约束中指定的最小值. 这个约束的参数是一个通过BigDecimal定义的最小值的字符串表示.小数存在精度
 * @Digits(message = "")     验证 Number 和 String 的构成是否合法
 * @Digits(integer=,fraction=,message = "") 验证字符串是否是符合指定格式的数字，interger指定整数精度，fraction指定小数精度。
 *
 * @Range(min=, max=,message = "") 检查数字是否介于min和max之间.
 *
 * @Valid 递归的对关联对象进行校验, 如果关联对象是个集合或者数组,那么对其中的元素进行递归校验,如果是一个map,则对其中的值部分进行校验.(是否进行递归验证)
 *
 * @CreditCardNumber(message = "") 信用卡验证
 * @Email(message = "")  验证是否是邮件地址，如果为null,不进行验证，算通过验证。
 * @ScriptAssert(lang= , script=, alias=,message = "")
 *
 * @URL(protocol=,host=, port=,regexp=, flags=,message = "")
 * @author li7hai26@gmail.com
 */
public class BeanValidators {
    public static void validateWithException(Validator validator, Object object, Class<?>... groups)
            throws ConstraintViolationException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}

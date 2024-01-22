package com.example.yxy.config;

import com.example.yxy.entity.Test16Form;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class FromToValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {

        // 只支持指定Bean类型的校验
        return Test16Form.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Test16Form form = (Test16Form) target;

		// 获取from和to的数字
        Integer fromNumber = form.getFromNumber();
        Integer toNumber = form.getToNumber();

		// 有任何一方为空，就不行校验
        if (ObjectUtils.isEmpty(fromNumber) || ObjectUtils.isEmpty(toNumber)) {
            return;
        }

        // 模拟从缓存或者session或者数据库中获取国际化消息
        Map<String, Object[]> languageErrorParamMap = new HashMap<String, Object[]>() {
            {
                put("zh", new Object[] { "开始数字", "结束数字" });
                put("ja", new Object[] { "スタートの数字", "エンドの数字" });
            }
        };

        // 获取当前设置地区的语言
        Locale locale = LocaleContextHolder.getLocale();
        String language = locale.getLanguage();
        Object[] errorParam = languageErrorParamMap.get(language);

		// 当from数字 大于 to数字的时候,进行业务校验
        if (fromNumber > toNumber) {
        	/*
        		参数1: bean中被校验住的属性名
        		参数2: 国际化资源文件中的key
        		参数3: error消息的参数
        		参数4: 默认消息
			*/
            errors.rejectValue("fromNumber", "1007E", errorParam, "");
        }
    }
}

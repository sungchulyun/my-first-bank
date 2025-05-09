package com.myfirstbank.finance_kids.global.meta;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
@ToString
@Getter
public class MetaInfos {

    private final Map<String, Object> metaInfos;

    protected MetaInfos() {
        metaInfos = new HashMap<>();
    }

    public Object findKey(String key){
        return metaInfos.get(key);
    }

    public MetaInfos add(String key, Object value){
        this.getMetaInfos().put(key, value);
        return this;
    }
}

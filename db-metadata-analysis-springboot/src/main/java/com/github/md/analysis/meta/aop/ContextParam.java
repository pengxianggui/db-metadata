package com.github.md.analysis.meta.aop;

import com.github.md.analysis.kit.Kv;
import lombok.Data;

/**
 * 用于aop链中传递额外上下文参数
 *
 * @author pengxg
 * @date 2023/10/13 11:30
 */
@Data
public class ContextParam extends Kv {

    /**
     * 异常
     */
    private Exception ex;
}

package com.baomidou.mybatisplus.test.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: miracle
 * Date: 2023/8/24 18:18
 */
@Data
public class TestDemo implements Serializable {

    private static final long serialVersionUID = 4648448703925966587L;

    @TableId(type = IdType.AUTO)
    private Long id;
    private String value;

}

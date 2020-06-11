<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${daoPackage}.dao.${tableNameCamel}Dao">

    <resultMap id="BaseResultMap" type="${boPackage}">
        <#list columnList as column>
        <result column="${column.name}" property="${column.nameCamel}"/>
        </#list>
    </resultMap>

    <sql id="Base_Column_List">
        <#list columnList as column>
        `${column.name}`<#if column_has_next>,</#if>
        </#list>
    </sql>

    <select id="selectById" parameterType="java.lang.Long" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from `${tableName}` where `id` = <#noparse>#{</#noparse>id<#noparse>}</#noparse>
    </select>

    <select id="selectOne" parameterType="${boPackage}" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from `${tableName}`
        <where>
            <#list columnList as column>
            <if test="null != ${column.nameCamel}">
                and `${column.name}` = <#noparse>#{</#noparse>${column.nameCamel}<#noparse>}</#noparse>
            </if>
            </#list>
        </where>
        limit 1
    </select>

    <select id="selectList" parameterType="${boPackage}" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from `${tableName}`
        <where>
            <#list columnList as column>
            <if test="null != ${column.nameCamel}">
                and `${column.name}` = <#noparse>#{</#noparse>${column.nameCamel}<#noparse>}</#noparse>
            </if>
            </#list>
        </where>
    </select>

    <select id="selectPager" parameterType="${entityPackage}.${tableNameCamel}DTO" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from `${tableName}`
        <where>
            <#list columnList as column>
            <if test="null != ${column.nameCamel}">
                and `${column.name}` = <#noparse>#{</#noparse>${column.nameCamel}<#noparse>}</#noparse>
            </if>
            </#list>
        </where>
        <if test="null != orderBy and '' != orderBy">
            order by <#noparse>${</#noparse>orderBy<#noparse>}</#noparse>
        </if>
        <if test="null != start and null != size">
            limit <#noparse>#{</#noparse>start<#noparse>}</#noparse>,<#noparse>#{</#noparse>size<#noparse>}</#noparse>
        </if>
    </select>

    <select id="selectAll" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from `${tableName}`
    </select>

    <select id="countAll" resultType="java.lang.Long">
        select
        count(1)
        from `${tableName}`
    </select>

    <select id="countPager" parameterType="${entityPackage}.${tableNameCamel}DTO" resultType="java.lang.Long">
        select
        count(1)
        from `${tableName}`
        <where>
            <#list columnList as column>
            <if test="null != ${column.nameCamel}">
                and `${column.name}` = <#noparse>#{</#noparse>${column.nameCamel}<#noparse>}</#noparse>
            </if>
            </#list>
        </where>
    </select>
    
    <insert id="insert" parameterType="${boPackage}" useGeneratedKeys="true" keyColumn="id" keyProperty="id">
        insert into `${tableName}` (
        <trim suffixOverrides=",">
            <#list columnList as column>
            <if test="null != ${column.nameCamel}">
                `${column.name}`,
            </if>
            </#list>
        </trim>
        ) values (
        <trim suffixOverrides=",">
            <#list columnList as column>
            <if test="null != ${column.nameCamel}">
                <#noparse>#{</#noparse>${column.nameCamel}<#noparse>}</#noparse>,
            </if>
            </#list>
        </trim>
        )
    </insert>
    
    <delete id="delete" parameterType="java.lang.Long">
        delete from `${tableName}` where `id` = <#noparse>#{</#noparse>id<#noparse>}</#noparse>
    </delete>

    <delete id="deleteBatch">
        delete from `${tableName}` where `id` in (
            <foreach collection="ids" item="id" separator=",">
                <#noparse>#{</#noparse>id<#noparse>}</#noparse>
            </foreach>
        )
    </delete>

    <update id="update" parameterType="${boPackage}">
        update `${tableName}`
        <set>
            <#list columnList as column>
            <if test="null != ${column.nameCamel}">
                `${column.name}` = <#noparse>#{</#noparse>${column.nameCamel}<#noparse>}</#noparse>,
            </if>
            </#list>
        </set>
        where `id` = <#noparse>#{</#noparse>id<#noparse>}</#noparse>
    </update>


</mapper>
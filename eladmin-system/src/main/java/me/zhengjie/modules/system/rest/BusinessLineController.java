/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.system.rest;

import cn.hutool.core.collection.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.config.RsaProperties;
import me.zhengjie.modules.system.dao.mapper.UserTestMapper;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.entity.BusinessLineDO;
import me.zhengjie.modules.system.domain.entity.UserTestDO;
import me.zhengjie.modules.system.service.*;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.vo.UserPassVo;
import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.modules.system.service.dto.UserQueryCriteria;
import me.zhengjie.utils.*;
import me.zhengjie.utils.enums.CodeEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Api(tags = "条线管理")
@RestController
@RequestMapping("/businessLine")
@RequiredArgsConstructor
public class BusinessLineController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final DataService dataService;
    private final DeptService deptService;
    private final RoleService roleService;
    private final VerifyService verificationCodeService;


    @Resource
    BusinessLineManageService businessLineManageService;

    @ApiOperation("新增条线")
    @PostMapping(value = "/add")
    public ResponseEntity<Object> addBusinessLine(@Validated @RequestBody BusinessLineDO businessLineDO){
        int rowAffected = businessLineManageService.add(businessLineDO);
        if (rowAffected > 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation("删除条线")
    @PostMapping(value = "/delete")
    public ResponseEntity<Object> deleteBusinessLine(@RequestBody int id){
        int rowAffected = businessLineManageService.delete(id);
        if (rowAffected > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }



}

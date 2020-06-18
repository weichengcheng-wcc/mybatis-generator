<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>代码生成器</title>
    <script src="/static/js/vue.min.js"></script>
    <link rel="stylesheet" href="/static/css/element.css">
    <script src="/static/js/element-ui.js"></script>
    <script src="/static/js/jquery-3.3.1.min.js"></script>
</head>
<body>
<center>
    <div id="app">
        <el-card>
            <!-- 条件配置 -->
            <div>
                <el-row :gutter="20">
                    <el-col :span="8">
                        <el-input placeholder="请输入Controller包名" v-model="downloadConfig.controllerPackage"
                                  size="mini">
                            <template slot="prepend">Controller</template>
                        </el-input>
                    </el-col>
                    <el-col :span="8">
                        <el-input placeholder="请输入Service包名" v-model="downloadConfig.servicePackage" size="mini">
                            <template slot="prepend">Service</template>
                        </el-input>
                    </el-col>
                    <el-col :span="8">
                        <el-input placeholder="请输入Dao包名" v-model="downloadConfig.daoPackage" size="mini">
                            <template slot="prepend">Dao</template>
                        </el-input>
                    </el-col>
                </el-row>
                <br>
                <el-row :gutter="20">
                    <el-col :span="8">
                        <el-input placeholder="请输入Pojo包名" v-model="downloadConfig.entityPackage" size="mini">
                            <template slot="prepend">Pojo</template>
                        </el-input>
                    </el-col>
                    <el-col :span="8">
                        <el-input placeholder="请输入作者名" v-model="downloadConfig.author" size="mini">
                            <template slot="prepend">Author</template>
                        </el-input>
                    </el-col>
                    <el-col :span="8">
                        <el-select v-model="downloadConfig.camelCase" placeholder="是否转驼峰" size="mini"
                                   style="width: 100%">
                            <el-option
                                    v-for="item in selectOption.camelCase"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-row>
            </div>

            <br>

            <!-- 搜索操作栏 -->
            <div>
                <div style="float: left">
                    <el-select v-model="searchConfig.dbName" placeholder="请选择数据库"
                               class="search" size="mini">
                        <el-option
                                v-for="item in selectOption.dbs"
                                :key="item"
                                :label="item"
                                :value="item">
                        </el-option>
                    </el-select>
                    <el-input placeholder="请输入表名" v-model="searchConfig.tableName" class="search" size="mini">
                    </el-input>
                    <el-input placeholder="请输入注释" v-model="searchConfig.comment" class="search" size="mini">
                    </el-input>
                    <el-button type="primary" size="mini" icon="el-icon-search" @click="getTables">搜索</el-button>
                </div>
                <div style="float: right">
                    <el-button type="primary" size="mini" icon="el-icon-download" @click="download">下载代码</el-button>
                </div>
            </div>

            <br>

            <!-- 内容展示区 -->
            <el-table
                    ref="table"
                    :data="tableData"
                    tooltip-effect="dark"
                    style="width: 100%"
                    size="mini"
                    @selection-change="handleSelectionChange">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        label="表名"
                        prop="tableSourceName"
                        width="180"
                        show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                        prop="dbName"
                        label="所属数据库"
                        width="180"
                        show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                        prop="engin"
                        label="存储引擎"
                        width="180"
                        show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                        prop="dataCount"
                        label="数据量"
                        width="150"
                        show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        label="创建时间"
                        width="180"
                        show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                        prop="comment"
                        label="表说明"
                        show-overflow-tooltip>
                </el-table-column>
            </el-table>

            <br>

            <!-- 分页 -->
            <el-pagination
                    background
                    layout="prev, pager, next"
                    @current-change="handleCurrentChange"
                    :page-size="searchConfig.size"
                    :total="pager.total">
            </el-pagination>

        </el-card>
    </div>
</center>
</body>
<script>
    vm = new Vue({
        el: '#app',
        data: {
            downloadConfig: {
                controllerPackage: "com.cw.frame.demo.rest",
                servicePackage: "com.cw.frame.demo.service",
                daoPackage: "com.cw.frame.demo.dao",
                entityPackage: "com.cw.frame.demo.entity",
                author: "C.W",
                camelCase: true,
                tableNames: null
            },
            searchConfig: {
                dbName: null,
                tableName: null,
                comment: null,
                start: 0,
                size: 15
            },
            selectOption: {
                camelCase: [{
                    name: "下划线转驼峰",
                    value: true
                }, {
                    name: "下划线不转驼峰",
                    value: false
                }],
                dbs: []
            },
            tableData: [],
            pager: {
                total: 0
            }
        },
        created: function () {
            this.getDbs();
        },
        methods: {
            // 获取所有的数据库
            getDbs() {
                $.ajax({
                    url: "/generator/getAllDbs",
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        vm.selectOption.dbs = data;
                        if (data.length != 0) {
                            vm.searchConfig.dbName = data[0];
                            // 获取表
                            vm.getTables();
                        }
                    }
                });
            },
            // 获取表
            getTables() {
                $.ajax({
                    url: "/generator/getAllTables",
                    type: "GET",
                    dataType: "json",
                    data: vm.searchConfig,
                    success: function (data) {
                        vm.pager.total = data.count;
                        vm.tableData = data.data;
                    }
                });
            },
            // 表选择变化
            handleSelectionChange(val) {
                let tableNames = '';
                val.forEach(function (item, index) {
                    tableNames = tableNames + item.tableSourceName + ",";
                })
                vm.downloadConfig.tableNames = tableNames;

            },
            // 分页变化
            handleCurrentChange(val) {
                vm.searchConfig.start = (val - 1) * vm.searchConfig.size;
                vm.getTables();
            },
            // 下载
            download() {
                vm.downloadConfig.dbName = vm.searchConfig.dbName;
                $.ajax({
                    url: "/generator/create",
                    type: "GET",
                    dataType: "text",
                    data: vm.downloadConfig,
                    success: function (data) {
                        console.log(data)
                        if (data) {
                            location.href = "/generator/download?path=" + data;
                        }
                    }
                });
            }
        }
    })
</script>
<style>
    #app {
        width: 1200px;
    }

    .search {
        width: 220px;
    }
</style>
</html>

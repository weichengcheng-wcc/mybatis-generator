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
<div id="app">
    <div style="float: right">
        <el-input v-model="table" style="width: 220px" placeholder="请输入表名"></el-input>
        <el-select v-model="selectDb" clearable placeholder="请选择数据库">
            <el-option
                    v-for="item in databases"
                    :key="item"
                    :label="item"
                    :value="item">
            </el-option>
        </el-select>
        <el-button type="primary" @click="getTables">搜索</el-button>
        <el-button type="primary" @click="exportCode">生成代码</el-button>
    </div>

    <br><br>
    <el-input v-model="createConfig.entityPackage" class="config" placeholder="请输入entityPackage">
        <template slot="prepend">entity包名</template>
    </el-input>
    <el-input v-model="createConfig.daoPackage" class="config" placeholder="请输入daoPackage">
        <template slot="prepend">dao包名</template>
    </el-input>
    <el-input v-model="createConfig.servicePackage" class="config" placeholder="请输入servicePackage">
        <template slot="prepend">service包名</template>
    </el-input>
    <el-input v-model="createConfig.controllerPackage" class="config" placeholder="请输入controllerPackage">
        <template slot="prepend">controller包名</template>
    </el-input>
    <el-input v-model="createConfig.author" class="config" placeholder="请输入author">
        <template slot="prepend">作者</template>
    </el-input>

    <br><br>

    <el-table
            :data="tableData"
            @selection-change="handleSelectionChange"
            v-loading="loading"
            style="width: 100%">
        <el-table-column
                type="index"
                width="50">
        </el-table-column>
        <el-table-column
                type="selection"
                width="55">
        </el-table-column>
        <el-table-column
                prop="tableName"
                label="表名"
                width="220">
        </el-table-column>
        <el-table-column
                prop="dbName"
                label="数据库名"
                width="220">
        </el-table-column>
        <el-table-column
                prop="engine"
                label="引擎"
                width="180">
        </el-table-column>
        <el-table-column
                prop="charSet"
                label="编码"
                width="180">
        </el-table-column>
        <el-table-column
                prop="createTime"
                label="创建时间"
                width="280">
        </el-table-column>
        <el-table-column
                prop="tableComment"
                label="注释">
        </el-table-column>
    </el-table>

    <br>
    <font>共 {{pager.total}} 条数据，当前显示 {{pager.total == 0?0:(pager.start+1}}-{{(pager.start+pager.size) >
        pager.total?pager.total:(pager.start+pager.size))}} 条</font>
    <el-pagination
            style="float: right;"
            background
            layout="prev, pager, next"
            :current-page.sync="pager.currentPage"
            :page-size="pager.size"
            @current-change="handleCurrentChange"
            :total="pager.total">
    </el-pagination>

</div>
</body>
<script>
    vm = new Vue({
        el: '#app',
        data: {
            tableData: [],
            databases: [],
            selectDb: '',
            table: '',
            pager: {
                start: 0,
                size: 10,
                currentPage: 1,
                total: 0
            },
            multipleSelection: [],
            loading: false,
            createConfig: {
                entityPackage: 'com.cw.frame.common.entity.system',
                daoPackage: 'com.cw.frame.system',
                servicePackage: 'com.cw.frame.system',
                controllerPackage: 'com.cw.frame.system',
                author: 'C.W'
            }
        },
        created: function () {
            this.getDatabses();
        },
        methods: {
            // 获取所有的表
            getTables: function () {
                vm.loading = true;
                $.ajax({
                    url: "/data/getTables",
                    data: {"start": vm.pager.start, "size": vm.pager.size, "db": vm.selectDb, "table": vm.table},
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        vm.tableData = data.data;
                        vm.pager.total = data.count;
                        vm.loading = false;
                    },
                    error: function (err) {
                        vm.loading = false;
                    }
                });
            },
            // 获取数据库
            getDatabses: function () {
                $.ajax({
                    url: "/data/getDatabases",
                    type: "GET",
                    dataType: "json",
                    success: function (data) {
                        vm.databases = data;
                        if (data.length != 0) {
                            vm.selectDb = data[0];
                            vm.getTables();
                        }
                    }
                });
            },
            // 分页调整
            handleCurrentChange: function (val) {
                vm.pager.start = (vm.pager.currentPage - 1) * vm.pager.size;
                // 获取翻页数据
                this.getTables();
            },
            // 批量选表
            handleSelectionChange: function (val) {
                vm.multipleSelection = val;
                console.log(vm.multipleSelection)
            },
            exportCode: function () {
                if (!vm.multipleSelection || vm.multipleSelection.length == 0) {
                    this.$message.error("请至少选择一个表生成代码");
                    return
                }

                vm.createConfig.tables = JSON.stringify(vm.multipleSelection);

                $.ajax({
                    url: "/data/export",
                    type: "POST",
                    data: vm.createConfig,
                    dataType: "text",
                    success: function (data) {
                        if (data) {
                            location.href = "/data/download?path=" + data;
                        }
                    },
                    error: function (err) {

                    }
                });
            }
        }
    })
</script>
<style>
    .config {
        width: 320px;
    }
</style>
</html>

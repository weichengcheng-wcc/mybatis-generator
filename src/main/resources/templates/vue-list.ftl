<template>
    <div>
        <div class="search-div">
            <#list  columnList as column>
				<el-input v-model="searchParam.${column.nameCamel}" class="search-input" placeholder="${column.comment}" size="mini"></el-input>
			</#list>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
        </div>
        <div class="action-div">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="add">新增</el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="deleteData">批量删除</el-button>
        </div>
        <el-table size="mini" :data="tableData" v-loading="config.loading.tableLoading" @selection-change="handleSelectionChange" style="width: 100%">
            <el-table-column type="selection" width="55"></el-table-column>
            <#list  columnList as column>
			<el-table-column prop="${column.nameCamel}" label="${column.comment}"></el-table-column>
			</#list>`
            <el-table-column fixed="right" label="操作" width="130">
                <template slot-scope="scope">
                          <el-button @click="detail(scope.row)" type="text" size="small">详情</el-button>
                          <el-button @click="edit(scope.row)" type="text" size="small">编辑</el-button>
                          <el-popconfirm title="一旦删除将无法恢复，请谨慎操作!" @onConfirm="delete(scope.row.id)">
                            <el-button slot="reference" type="text" size="small">删除</el-button>
                          </el-popconfirm>
</template>
      </el-table-column>
    </el-table>
  </div>
</template>
    </el-table-column>
    </el-table>
    <br/>
    <div>
      <span>共 <b>{{pager.total}}</b> 条数据，当前展示 <b>{{pager.total != 0?(Number(pager.start)+1):0}}</b>-<b>{{((Number(pager.start)+pager.size)) > pager.total?pager.total:(Number(pager.start)+pager.size)}}</b> 条</span>
      <el-pagination
        background
        class="pager-right"
        layout="prev, pager, next"
		    :page-size="pager.size"
        :current-page="pager.currentPage"
        @current-change="handleCurrentChange"
        :total="pager.total">
      </el-pagination>
    </div>

    <!-- 弹框 -->
    <detail ref="detail" />
	<edit ref="edit" />
	<add ref="add" />

  </div>
</template>

<script>
import detail from "./detail";
import edit from "./edit";
import add from "./add";

export default {
    name: "index",
    components: {
        detail: detail,
        edit: edit,
        add: add
    },
    data() {
        return {
            config: {
                loading: {
                    tableLoading: false
                }
            },
            tableData: [],
            searchParam: {
				<#list columnList as column>
				${column.nameCamel}:this.$route.query.${column.nameCamel} ? this.$route.query.${column.nameCamel} : null<#if column_has_next>,</#if>
				</#list>
			},
            multipleSelection: [],
            pager: {
                start: this.$route.query.start ? this.$route.query.start : 0,
                size: 20,
                total: 1,
                currentPage: this.$route.query.start ?
                    this.$route.query.start / 20 + 1 : 1
            }
        };
    },
    created() {
        if (this.pager.start > this.pager.total) {
            this.pager.start =
                parseInt(this.pager.total / this.pager.size) * this.pager.size;
            this.$router
                .push({ name: "", query: { start: this.pager.start } })
                .catch(data => {});
        }
        this.getList();
    },
    methods: {
        // 删除单个
        delete(id) {
            // 删除
            let url = "";
            this.del(url, { id: id })
                .then(res => {
                    if (res.status == 200) {
                        this.$message.success("删除成功");
                    } else {
                        this.$message.error(res.msg);
                    }
                    this.getList();
                })
                .catch(err => {});
        },
        // 删除数据
        deleteData() {
            if (this.multipleSelection.length == 0) {
                this.$message.warning("请至少选择一条数据");
                return;
            }
            this.$confirm("此操作将永久删除, 是否继续?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                })
                .then(() => {
                    // 删除
                    let url = "";
                    this.del(url, this.multipleSelection)
                        .then(res => {
                            if (res.status == 200) {
                                this.$message.success("删除成功");
                            } else {
                                this.$message.error(res.msg);
                            }
                            this.getList();
                        })
                        .catch(err => {});
                })
                .catch(() => {});
        },
        // 查看详情
        detail(data) {
            this.$refs.detail.showDialog(data);
        },
        // 编辑数据
        edit(data) {
            this.$refs.edit.showDialog(data);
        },
        // 新增数据
        add() {
            this.$refs.add.showDialog();
        },
        // 获取数据
        getList() {
            let url = "";
			let param = this.searchParam;
            param.start = this.pager.start;
            param.size = this.pager.size;
            this.config.loading.tableLoading = true;
            this.get(url, param)
                .then(res => {
                    if (res.status == 200) {
                        this.tableData = res.body.data.data;
                        this.pager.total = res.body.data.count;

                        // 判断如果本页没有数据，计算出最大有数据的页数
                        if (
                            this.pager.total != undefined &&
                            this.pager.total != null &&
                            this.pager.total > 0 &&
                            (this.tableData == undefined ||
                                this.tableData == null ||
                                this.tableData == [])
                        ) {
                            this.pager.start =
                                parseInt(this.pager.total / this.pager.size) * this.pager.size;
                            this.$router
                                .push({ name: "", query: { start: this.pager.start } })
                                .catch(data => {});
                            this.getList();
                        }
                    }

                    this.config.loading.tableLoading = false;
                })
                .catch(err => {
                    this.config.loading.tableLoading = false;
                });
        },
        // table多选
        handleSelectionChange(val) {
            this.multipleSelection = [];
            val.forEach(va => {
                this.multipleSelection.push(va.id);
            });
        },
        // 分页
        handleCurrentChange(val) {
            this.pager.start = (val - 1) * this.pager.size;
            this.$router
                .push({ name: "", query: { start: this.pager.start } })
                .catch(data => {});
            // 获取数据
            this.getList();
        },
		// 搜索
		search(){
			// 放入参数
            let params = this.searchParam;
			this.$router.push({
                name: "",
                query: {
                    <#list columnList as column>
                    ${column.nameCamel}:params.${column.nameCamel}<#if column_has_next>,</#if>
                    </#list>
                }
			});
			this.getList();
		}
    }
}
</script>

<style scoped>
.search-input {
    width: 200px;
}

.search-div {
    float: left;
}

.pager-right {
    float: right;
}

.pager-left {
    float: left;
}

.action-div {
    float: right;
}
</style>

<template>
    <el-dialog title="新增" :visible.sync="config.dialogVisible" width="30%">
        <el-form :model="formData" ref="form" :rules="rules">
			<#list columnList as column>
            <el-form-item label="${column.comment}" label-width="80px" prop="${column.nameCamel}">
                <el-input v-model="formData.${column.nameCamel}" placeholder="${column.comment}"></el-input>
            </el-form-item>
			</#list>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="closeDialog">取 消</el-button>
            <el-button type="primary" @click="add" :loading="config.loading.btnLoading">确 定</el-button>
          </span>
    </el-dialog>
</template>

<script>
export default {
    name: "add",
    data() {
        return {
            formData: {},
            config: {
                dialogVisible: false,
                loading: {
                    btnLoading: false
                }
            },
            rules: {
				<#list columnList as column>
                ${column.nameCamel}: [{
                    required: true,
                    message: "${column.comment}不能为空",
                    trigger: "blur"
                }]<#if column_has_next>,</#if>
				</#list>
            }
        }
    },
    methods: {
        // 打开弹框
        showDialog() {
            this.formData = {};
            this.config.dialogVisible = true;
        },
        // 关闭弹框
        closeDialog() {
            this.config.dialogVisible = false;
        },
        // 新增数据
        add() {
            this.$refs['form'].validate(valid => {
                this.config.loading.btnLoading = true;
                let url = ""
                this.post(url, this.formData).then(res => {
                    this.config.loading.btnLoading = false;
                }).catch(err => {
                    this.config.loading.btnLoading = false;
                })
            })
        }
    }
}
</script>

<style scoped>

</style>

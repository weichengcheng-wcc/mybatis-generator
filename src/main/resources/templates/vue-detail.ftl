<template>
  <el-dialog title="详情" :visible.sync="config.dialogVisible" width="30%">
    <el-form :model="formData">
	  <#list columnList as column>
      <el-form-item label="${column.comment}" label-width="80px">
        <el-input v-model="formData.${column.nameCamel}" placeholder="${column.comment}" disabled="disabled"></el-input>
      </el-form-item>
	  </#list>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeDialog">取 消</el-button>
      <el-button type="primary" @click="closeDialog">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  name: "detail",
  data() {
    return {
      formData: {},
      config: {
        dialogVisible: false
      }
    };
  },
  methods: {
    // 打开弹框
    showDialog(data) {
      this.formData = data;
      this.config.dialogVisible = true;
    },
    // 关闭弹框
    closeDialog() {
      this.config.dialogVisible = false;
    }
  }
};
</script>

<style scoped>
</style>

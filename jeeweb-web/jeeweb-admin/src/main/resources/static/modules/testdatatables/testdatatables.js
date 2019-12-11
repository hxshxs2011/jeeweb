/**
 * Created by xianshun.huang on 2018/12/10.
 */

setDataTable();
set_view_log_tab_oracle();

function setDataTable()
{
    $('#view_log_tab').append('<caption style="caption-side: bottom"></caption>');
    var table = $('#view_log_tab').DataTable(
        {
            "language" : {
                "lengthMenu" : "每页 _MENU_ 条记录",
                "zeroRecords" : "没有找到记录",
                "info" : "第 _PAGE_ 页 ( 共 _TOTAL_ 条数据，合_PAGES_ 页 )",
                "infoEmpty" : "无记录",
                "infoFiltered" : "(从 _MAX_ 条记录过滤)",
                "sSearch" : "智能查询:",
                "oPaginate" : {
                    "sFirst" : "首页",
                    "sPrevious" : "前一页",
                    "sNext" : "后一页",
                    "sLast" : "尾页"
                }
            },
            "ajax" : '/DataTables_1/getshuj',
            "columns" : [ {
                "data" : "APPROVAL_PERSON"
            }, {
                "data" : "REMARK"
            }, {
                "data" : "APPROVAL_DATE"
            }, ],

            dom: 'Bfrtip',
            buttons: [
                'copy',
                {
                    extend: 'excel',
                    messageTop: '导出顶部信息'
                },
            ]

        })


}

function set_view_log_tab_oracle()
{
    $('#view_log_tab_oracle').append('<caption style="caption-side: bottom"></caption>');
    var table = $('#view_log_tab_oracle').DataTable(
        {
            "language" : {
                "lengthMenu" : "每页 _MENU_ 条记录",
                "zeroRecords" : "没有找到记录",
                "info" : "第 _PAGE_ 页 ( 共 _TOTAL_ 条数据，合_PAGES_ 页 )",
                "infoEmpty" : "无记录",
                "infoFiltered" : "(从 _MAX_ 条记录过滤)",
                "sSearch" : "智能查询:",
                "oPaginate" : {
                    "sFirst" : "首页",
                    "sPrevious" : "前一页",
                    "sNext" : "后一页",
                    "sLast" : "尾页"
                }
            },
            "serverSide" : true,
            "bStateSave" : false,
            "ajax": {
                "url": '/DataTables_1/getshuj_from_oracle',
                "dataType": "json"
            },
            "columnDefs" : [//隐藏列从0开始
                {
                    "targets" : [ 0 ],
                    "visible" : true,
                    "searchable" : true,
                }, {
                    "targets" : [ 1 ],
                    "visible" : true,
                    "searchable" : true
                }, {
                    "targets" : [ 2 ],
                    "visible" : true,
                    "searchable" : true
                },],
            "columns" : [ {
                "data" : "XM_NAME"
            }, {
                "data" : "XM_B_DATE"
            }, {
                "data" : "XM_HYZKSJ"
            }, ],

            dom: 'Bfrtip',
            buttons: [
                'copy',
                {
                    extend: 'excel',
                    messageTop: '导出顶部信息'
                },
            ]

        })


}


// 打开对话框(添加修改)
function open_Web_Window(title, url, width, height, target) {

    width = width ? width : '800px';
    height = height ? height : '500px';
    if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {// 如果是移动端，就使用自适应大小弹窗
        width = 'auto';
        height = 'auto';
    } else {// 如果是PC端，根据用户设置的width和height显示。

    }
    top.layer.open({
        type : 2,
        area : [ width, height ],
        title : title,
        maxmin : true, // 开启最大化最小化按钮
        content : url,
        btn : [ '确定5', '关闭' ],
        yes : function(index, layero) {
           /* var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            //console.log(iframeWin.contentWindow.document.getElementById("testform"));
            iframeWin.contentWindow.document.getElementById("testform")
            top.layer.close(index);
            // 文档地址
            // http://www.layui.com/doc/modules/layer.html#use
            /!*iframeWin.contentWindow.doSubmit(top, index, function() {
                // 刷新表单
                //refreshTable('contentTable');
                alert("刷新")
            });*!/*/

            var body = top.layer.getChildFrame('body', index);
            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            // 文档地址
            // http://www.layui.com/doc/modules/layer.html#use
            iframeWin.contentWindow.doSubmit(top, index, function() {
                // 刷新表单
                su();
            });

        },
        cancel : function(index) {
        }
    });

}


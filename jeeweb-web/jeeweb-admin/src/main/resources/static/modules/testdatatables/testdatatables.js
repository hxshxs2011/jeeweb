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


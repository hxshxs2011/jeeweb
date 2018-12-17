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
            "ajax" : '/DataTables_1/getshuj_from_oracle',
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


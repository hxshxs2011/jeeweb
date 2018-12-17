/**
 * Created by xianshun.huang on 2018/12/10.
 */

pageInit();
function pageInit()
{
    jQuery("#list2").jqGrid(
        {
            url : "/jqGrid/getshuj_from_oracle",
            datatype : "json",
            colNames : [ '昵称', '邮件', '状态', 'ID号', '生日' ],
            colModel : [
                {name : 'NICKNAME'},
                {name : 'EMAIL'},
                {name : 'STATUS'},
                {name : 'ID',sortable:true},
                {name : 'BIRTHDAY',sortable:true},
            ],
            rowNum : 10,
            rowList : [ 10, 20, 30 ],
            pager : '#pager2',
            sortname : 'id',
            mtype : "post",
            viewrecords : true,
            sortorder : "desc",
            caption : "JSON 实例"
        });
    jQuery("#list2").jqGrid('navGrid', '#pager2', {edit : true,add : true,del : true});

}


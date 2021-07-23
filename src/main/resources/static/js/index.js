$(function () {
    $("#jqGrid").jqGrid({
        url: 'project/list',
        datatype: "json",
        colModel: [			
			{ label: '项目id', name: 'id', index: "id", width: 45, key: true,hidden:true },
			{ label: '项目名称', name: 'projectName', width: 75 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});
//生成菜单
var menuItem = Vue.extend({
	name: 'menu-item',
	props:{item:{}},
	template:[
		'<li>',
		'	<a v-if="item.type === 0 && item.arrange != 1" href="javascript:;">',
		'		<i v-if="item.icon != null" :class="item.icon"></i>',
		'		<span>{{item.name}}</span>',
		'		<i v-if="item.arrange != 1" class="fa fa-angle-left pull-right"></i>',
		'	</a>',
		'	<ul v-if="item.type === 0 " class="treeview-menu">',
		'		<menu-item :item="item1" v-if="item.arrange!=1"  v-for="item1 in item.list"></menu-item>',
		'	</ul>',	
		'	<a v-if="item.type === 0 && item.arrange===1" :href="\'#tabMenu.html?menuId=\'+item.menuId+\'\'"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',			
		'	<a v-if="item.type === 1" :href="\'#\'+item.url"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
		'</li>'
	].join('')
});

//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 120);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

//注册菜单组件
Vue.component('menuItem',menuItem);

var vm = new Vue({
	el:'#rrapp',
	data:{
		user:{},
		menuList:{},
		main:"main.html",
		password:'',
		newPassword:'',
        navTitle:"控制台",
        projectName:"选择项目"
	},
	methods: {
		getMenuList: function (event) {
			$.getJSON("sys/menu/nav?_"+$.now(), function(r){
				vm.menuList = r.menuList;
			});
		},
		getUser: function(){
			$.getJSON("sys/user/info?_"+$.now(), function(r){
				vm.user = r.user;
				vm.getProjecName();
			});
		},
		getProjecName: function(){
			var projectId = vm.user.projectId;
			if(projectId!=null){
				$.getJSON("project/info/"+projectId, function(r){
					vm.projectName = r.projectInfo.projectName;
				});					
			}
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['550px', '270px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "password="+vm.password+"&newPassword="+vm.newPassword;
					$.ajax({
						type: "POST",
					    url: "sys/user/password",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
	            }
			});
		},
        donate: function () {
            layer.open({
                type: 2,
                title: false,
                area: ['806px', '467px'],
                closeBtn: 1,
                shadeClose: false,
                content: ['http://cdn.renren.io/donate.jpg', 'no']
            });
        },
        getProjects: function(){
			this.reload();
            layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "选择项目",
				area: ['850px', '400px'],
				shadeClose: false,
                shade: 0,
				content: jQuery("#projectLayer"),
				btn: ['确定','取消'],
				btn1: function (index) {
		            var projectId = getSelectedRow();
		            if(projectId == null){
		                return ;
		            }
					var data = "projectId="+projectId;
					$.ajax({
						type: "POST",
					    url: "sys/user/updateProject",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
                }
            });
        },
        reload: function () {
           // vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
               // postData:{'projectName': ""},
                page:page
            }).trigger("reloadGrid");
        }
	},
	created: function(){
		this.getMenuList();
		this.getUser();
	},
	updated: function(){
		//路由
		var router = new Router();
		routerList(router, vm.menuList);
		router.start();
	}
});



function routerList(router, menuList){
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.type == 0){
			if(menu.arrange==1){
				var id = menu.menuId;	
				router.add('#tabMenu.html?menuId='+menu.menuId, function() {
					var url = window.location.hash;	
					//替换iframe的url
				    vm.main = url.replace('#', '');			   
				    //导航菜单展开
				    $(".treeview-menu li").removeClass("active");
				    $("a[href='"+url+"']").parents("li").addClass("active");			 
				    vm.navTitle = $("a[href='"+url+"']").text();
				});				
			}else{
				routerList(router, menu.list);				
			}
		}else if(menu.type == 1){
			router.add('#'+menu.url, function() {
				var url = window.location.hash;
				//替换iframe的url
			    vm.main = url.replace('#', '');			   
			    //导航菜单展开
			    $(".treeview-menu li").removeClass("active");
			    $("a[href='"+url+"']").parents("li").addClass("active");			    
			    vm.navTitle = $("a[href='"+url+"']").text();
			});
		}
	}
}

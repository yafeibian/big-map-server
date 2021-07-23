$(function () {	
    function getHeight() {
        var  h = ($(window).height()-80);
        if(h>=500){
            return h;
        }
    }
    console.log(T.p('menuId'));
});

var vm = new Vue({
    el:'#rrapp',
    data:{
    	menuList:[],
    	menuId:null
    },
    mounted(){

    },
    methods: {
        goto: function(url){    
		      $('#iframe').attr("src", url); 
        },
		getMenuList: function () {
			$.getJSON("sys/menu/queryListParentId/"+this.menuId+"?_"+$.now(), function(r){
				vm.menuList = r.menuList;
				if(r.menuList!=null && r.menuList.length>0){				
					vm.goto(r.menuList[0].url);
				}
			});
		},		       
    },
	created: function(){
		this.menuId = T.p('menuId');
		this.getMenuList();
	},
	updated: function(){
		
	}
})


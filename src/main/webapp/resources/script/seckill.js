//存放交互逻辑的js代码
//javascript模块化

var seckill = {
		//封装ajax相关的url
		URL:{
			now:function(contextPath){
				return contextPath + '/seckill/time/now';
			},
			expose:function(seckillId,contextPath){
				
				return contextPath + '/seckill/' + seckillId + '/expose';
			},
			execute:function(seckillId,md5,contextPath){
				return contextPath + '/seckill/' + seckillId + "/" + md5 + "/execute";
				//return contextPath + '/seckill/' + seckillId + "/" + md5 + "/executeProcedure";
			}
		},
		//获取秒杀地址，执行秒杀方法
		handleSeckill:function(seckillId,node,contextPath){
			//隐藏node原来的对象，改变原来的html元素
			node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
			//获取秒杀地址，从获取秒杀地址的接口判断客户端时间是否可以执行秒杀方法
			$.post(seckill.URL.expose(seckillId,contextPath),{},function(result){
				//判断返回结果是否可以执行秒杀方法
				if(result && result.success){//秒杀时间开启了
					var exposer = result.data;
					//判断exposer秒杀接口对象是否true，true则可以执行秒杀方法
					if(exposer.exposed){
						var md5 = exposer.md5;
						//获得秒杀url
						var killUrl = seckill.URL.execute(seckillId,md5,contextPath);
						console.log(killUrl);
	                    //.one()绑定一次点击事件，意思是只能点击一次，
						//如果使用('#killBtn').click()-->这样就会一致绑定着，这样用户就可以连续点击
						$('#killBtn').one('click',function(){
							//把按钮设置disabled
							$(this).addClass('disabled');
							//ajax post方法执行点击按钮，秒杀方法
							console.log("in click");
							console.log("=="+killUrl);
							$.post(killUrl,{},function(result){
								console.log(result);
								if(result && result.success){
									var seckillExecution = result.data;
									var state = seckillExecution.state;
									var stateInfo = seckillExecution.stateInfo;
									//显示秒杀结果
									node.html('<span class="label label-success">' + stateInfo + '</span>');
								}
							});
						});
						node.show();//先显示秒杀按钮，然后当点击按钮后，这个node变成秒杀的结果。
					}else{//秒杀方法未开始，这种情况是客户端的时间和服务器时间有差异了
						var now = exposer.nowTime;
						var start = exposer.startTime;
						var end = exposer.endTime;
						seckill.countdown(seckillId,now,start,end,contextPath);
					}
				}else{
					console.log("result:" + result);
				}
			});
			
		},
		//验证手机号格式
		validatePhone:function(phone){
			if(phone && phone.length == 11 && !isNaN(phone)){
				return true;
			}else{
				return false;
			}
		},
		//与服务器的计时交互方法
		countdown:function(seckillId,nowTime,startTime,endTime,contextPath){
			//1、获取seckill-box的span节点对象
			var seckillBox = $('#seckill-box');
			//2、判断当前时间与秒杀开启、结束时间的关系
			//2.1当前时间大于结束时间，秒杀已结束
			if(nowTime > endTime){
				console.log("===========nowTime==" + nowTime);
				console.log("===========endTime==" + endTime);
				seckillBox.html('秒杀结束');
			}else if(nowTime < startTime){//2.2当前时间小于开启时间，秒杀未开始
				//加上1秒，因为客户端请求服务器时间再到返回给浏览器，也需要时间，所以
				//把这个时间加上1秒，以防止客户端的时间比服务器端的时间有偏差。
				var killTime = new Date(startTime + 1000);
				//使用jquery的countdown倒计时插件
				//用法十分简单，写一个div，span这样的容器，然后直接用容器调用countdown方法，传入起始倒计时的时间，和回调方法即可。
				seckillBox.countdown(killTime,function(event){
					var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
					seckillBox.html(format);
				}).on('finish.countdown',function(){//当秒杀倒计时结束时，用户获取秒杀地址
					seckill.handleSeckill(seckillId,seckillBox,contextPath);
				});
			}else{//2.3当前时间在开启时间和结束时间之间，用户获取秒杀地址
				seckill.handleSeckill(seckillId,seckillBox,contextPath);
			}
		},
		//详情页json封装，以上的3个方法都是给detail对象使用的
		detail:{
			init:function(params,contextPath){
				//手机验证和登录，计时交互
				//规划交互流程
				//1、获取cookie的手机号
				var killPhone = $.cookie('killPhone');//$.cookie = jQuery.cookie
				//2、验证cookie中的手机号格式，如果格式不正确，或者cookie中没有手机号
				console.log(killPhone);
				if(!seckill.validatePhone(killPhone)){
					//获得输入手机的div对象
					var killPhoneModal = $('#killPhoneModal');
					//显示这个div层
					killPhoneModal.modal({
						show:true,
						backdrop:'static',
						keyboard:false
					});
					//点击按钮，获取验证输入的手机号，这个交互是在客户端用js实现的，手机号被保存在cookie中了，表示用户登录了。
					$('#killPhoneBtn').click(function(){
						//获得输入的手机号
						var inputPhone = $('#killPhoneKey').val();
						console.log('inputPhone=' + inputPhone);
						//验证输入的手机号格式
						if(seckill.validatePhone(inputPhone)){
							//把手机号保存在cookie中
							$.cookie('killPhone',inputPhone,{expires:7,path:contextPath+'/seckill'});//path：指的这个cookie只在这个路径下有效
							window.location.reload();
						}else{
							//$('#killPhoneMessage').hide()返回的是一个jquery对象，所以可以直接调用html("")方法。
							//作用：隐藏$('#killPhoneMessage')这个对象，变成html('')的内容
							//只是调用html还是隐藏的状态，调用show方法则会显示出来，300为显示速度。
							$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号不正确！</label>').show(300);
						}
					})
				}
				var seckillId = params.seckillId;
				var startTime = params.startTime;
				var endTime = params.endTime;
				
				//3、已经登录，计时交互，获取服务器时间
				//ajax get请求，获取服务器时间
				$.get(seckill.URL.now(contextPath), {}, function(result){
					if(result && result.success){
						//ajax请求当前服务器时间，获取result结果，从result中去服务器时间
						var nowTime = result.data;
						//通过服务器的时间进行计时交互
						seckill.countdown(seckillId,nowTime,startTime,endTime,contextPath);
					}else{
						console.log("result=="+result);
					}
				});
				
			}
		}
}
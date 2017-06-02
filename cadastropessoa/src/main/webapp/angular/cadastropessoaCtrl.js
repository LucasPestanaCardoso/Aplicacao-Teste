	angular.module("cadastroPessoa", []);
		angular.module("cadastroPessoa").controller("cadastropessoaCtrl", function ($scope, $http) {
			$scope.app = "Cadastro Pessoa";
		
		   var isAlteracao = false;
		        
		   var carregarContatos = function () {
					
			   $http.get("pessoa/carregar").then(successCallback);

			   function successCallback(response){
					$scope.contatos = response.data;
			   }
		      
		   };     
			
		   $scope.adicionarContato = function (contato) {
			  			   
			   if(isAlteracao){
				   				   
				   contato.nome = contatoForm.nome.value;
				   contato.cpf =  contatoForm.cpf.value;
				   contato.data =  contatoForm.data.value;
				   contato.telefone = contatoForm.telefone.value; 
			
				   $http.post("pessoa/alterar/" , contato).then(function(data) {
					   delete $scope.contato;
					   $scope.contatoForm.$setPristine();
					   carregarContatos();
				   });
				   
			   }else{
				   $http.post("pessoa/salvar", contato).then(successCallback , errorCallback);
					   
				   function successCallback(response , data){
					   
					   	delete $scope.contato;
						$scope.contatoForm.$setPristine();
						carregarContatos();
				   }
				   
			   }
			   
			   isAlteracao = false;
		   };

	       $scope.apagarContato = function (contato) {
	            
	    	   $http.delete("pessoa/excluirRegistro/" + contato.nome).then(function(data) {
	    		   carregarContatos();
			   });
	    	   
	       };
	       
	       $scope.gerarExcel = function (contatos) {
	    	   $http({
	    		    url: 'pessoa/gerarExcel',
	    		    method: 'POST',
	    		    responseType: 'arraybuffer',
	    		    data: contatos, // this is your json data string
	    		    headers: {
	    		        'Content-type': 'application/json',
	    		        'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
	    		    }}).then(successCallback);
	    	   
	    	   
	    	   function successCallback(data, status, headers, config){
				   
	    		   	var blob = new Blob([data], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
	    		    var objectUrl = URL.createObjectURL(blob);
	    		    window.open(objectUrl);
			   }
	    	   
	       };
	       
	       $scope.alterarContato = function (contato) {
	            
	    	  contatoForm.nome.value = contato.nome;
	    	  contatoForm.cpf.value = contato.cpf;
	    	  contatoForm.data.value = contato.data;
	    	  contatoForm.telefone.value = contato.telefone;
	    	  isAlteracao = true; 
	       };
		      
	       carregarContatos(); 
		       
		});
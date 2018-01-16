# WaterLink
Projeto Final Computação Movel 

O projeto consiste na criação de uma aplicação que auxilia os técnicos operacionais de uma empresa de distribuição de àgua e saneamento.

A aplicação, em background, importa um ficheiro json onde estão todas as infraestruturas da empresa e suas caracteristicas.
Após o "get", introduz alguns dos dados numa base de dados local SQlite.

A aplicação é composta por 2 activities (a main e infraDetail) sendo que:
  - a primeira apresenta uma listView com as infraestruturas, uma searchBox para busca rápida e um botão de Sync (sincroniza os dados do json com a base de dados e com a app, garantindo que qualquer alteração ao ficheiro, é atualizada na DB).
  - a segunda activity apresenta uma WebView com um staticMap com referência da localização da infraestrutura escolhida, um textView com alguns detalhes sobre a infraestrutura, uma editText para colocar observações, um botão de back, um botão para upload das observações e um botão que vai redireccionar para o googleMaps, exportando as coordenadas, permitindo ao utilizador recorrer ao GPS para se dirigir para a infraestrutura. 
  
  json file:
  https://waterlink.addp.pt/infraestrutura/getinfraestruturas
  Anexo aos ficheiro, segue um ficheiro getinfraestruturas.json, caso haja falha com o servidor. Esperemos que não.
  
  O ficheiro é publico, no entanto é do conhecimento do coordenador do SI das Aguas do Douro e Paiva e Simdouro a utilização do mesmo para fins académicos.
  
  A app foi devidamente testada, não tem nenhum bug aparente, embora o aspeto visual não seja muito "robusto".
  
 NOTA: Na primeira vez que os dados são carregados, basta escrever uma letra na searchBox para aparecer a lista pela primeira vez, daz vezes seguinte carrega automáticamente.
  
  Atentamente 
  
  
  
  


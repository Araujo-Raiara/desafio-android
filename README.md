# PicPay - Desafio Android

<img src="https://github.com/mobilepicpay/desafio-android/blob/master/desafio-picpay.gif" width="300"/>

Um dos desafios de qualquer time de desenvolvimento é lidar com código legado e no PicPay isso não é diferente. Um dos objetivos de trazer os melhores desenvolvedores do Brasil é atacar o problema.
Para isso, essa etapa do processo consiste numa proposta de solução para o desafio abaixo e você pode escolher a melhor forma de resolvê-lo, de acordo com sua comodidade e disponibilidade de tempo:
- Resolver o desafio previamente, e explicar sua abordagem no momento da entrevista.
- Discutir as possibilidades de solução durante a entrevista, fazendo um pair programming (bate-papo) interativo com os nossos devs.

Com o passar do tempo identificamos alguns problemas que impedem esse aplicativo de escalar e acarretam problemas de experiência do usuário. A partir disso elaboramos a seguinte lista de requisitos que devem ser cumpridos ao melhorar nossa arquitetura:

- Em mudanças de configuração o aplicativo perde o estado da tela. Gostaríamos que o mesmo fosse mantido.
- Gostaríamos de cachear os dados retornados pelo servidor.
  
Solução: Utilizei o SavedStateHandle com a ViewModel para guardar os valores e recuperá-los quando houver alguma mudança inesperada.

- Nossos relatórios de crash têm mostrado alguns crashes relacionados a campos que não deveriam ser nulos sendo nulos e gerenciamento de lifecycle. Gostaríamos que fossem corrigidos.
  
Solução: Tornei os campos nullable, removi toda a lógica do onResume e criei useCases para a lógica de negócios.

- Haverá mudanças na lógica de negócios e gostaríamos que a arquitetura reaja bem a isso.
- Haverá mudanças na lógica de apresentação. Gostaríamos que a arquitetura reaja bem a isso.
  
Solução: Optei pela arquitetura MVVM para aprimorar a capacidade de resposta a mudanças futuras na lógica de negócios e apresentação.
Separando a responsabilidade entre os componentes, promovendo uma arquitetura mais modular e facilitando a manutenção. 

 - Com um grande número de desenvolvedores e uma quantidade grande de mudanças ocorrendo testes automatizados são essenciais.
  - Gostaríamos de ter testes unitários testando nossa lógica de apresentação, negócios e dados independentemente, visto que tanto a escrita quanto execução dos mesmos são rápidas.
  - Por outro lado, testes unitários rodam em um ambiente de execução diferenciado e são menos fiéis ao dia-a-dia de nossos usuários, então testes instrumentados também são importantes.
    
    Solução: Para garantir a qualidade do código, realizei testes unitários utilizando a biblioteca Mockk.
     Esses testes cobrem a lógica de apresentação, negócios e dados.
    Utilizei o Espresso para testes instrumentados.

## Outras implementações:
- Implementei o searchView para possibilitar a pesquisa de contatos
- Adicionei o componente de navegação (Jetpack Components)
- Injeção de dependencias utilizando o Koin
- Melhorias no layout
- Update das dependencias (Gradle)
- Coroutines
- LiveData
- ViewBinding
- okhttp3
  
  ![Screen_recording_20240213_210526](https://github.com/Araujo-Raiara/desafio-android/assets/62944970/73348c2f-549a-433b-861b-1b7c8d055f3f)

Boa sorte! =)
Ps.: Fique à vontade para editar o projeto inteiro, organização de pastas e módulos, bem como as dependências utilizadas

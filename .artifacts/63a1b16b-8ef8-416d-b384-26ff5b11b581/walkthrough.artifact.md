# Walkthrough - Tela de Abertura (Splash Screen)

Implementei uma nova tela de abertura que exibe a logo do aplicativo em tela cheia com uma transição suave.

## Alterações Realizadas

### Nova Tela (Splash)
- **Componente**: Criei o arquivo [SplashScreen.kt](file:///C:/Users/LenovoGames/AndroidStudioProjects/OuvirNorteMG2/app/src/main/java/britoinfo/ouvirnortemg/ui/SplashScreen.kt).
- **Visual**: A imagem `logo.jpg` agora é exibida em tela cheia (`ContentScale.Crop`) com fundo preto.
- **Animação**: Adicionei um efeito de "Fade In" (surgimento gradual) que dura 1,5 segundos.

### Integração
- **MainActivity**: Atualizei a [MainActivity.kt](file:///C:/Users/LenovoGames/AndroidStudioProjects/OuvirNorteMG2/app/src/main/java/britoinfo/ouvirnortemg/MainActivity.kt) para gerenciar o tempo de exibição.
- **Temporizador**: A tela de abertura fica visível por 3 segundos.
- **Transição**: Usei um `Crossfade` para que a tela de abertura desapareça suavemente enquanto a lista de rádios aparece.

## Verificação

- **Build**: O projeto compilou com sucesso (`assembleDebug`).
- **Fluxo**: O aplicativo inicia com a logo grande e, após 3 segundos, muda para a interface principal de rádio.

> [!TIP]
> Essa tela de abertura dá um tempo para o aplicativo inicializar o serviço de áudio e carregar as primeiras logos da internet em segundo plano, melhorando a experiência do usuário.

# Implementação de Tela de Abertura (Splash Screen) em Tela Cheia

Este plano detalha a criação de uma tela de abertura (Splash Screen) que exibe a logo do aplicativo em tamanho grande, cobrindo a tela toda antes de mostrar a lista de rádios.

## User Review Required

> [!IMPORTANT]
> A nova tela de abertura aparecerá por aproximadamente **3 segundos** toda vez que o aplicativo for iniciado.
> A imagem `logo.jpg` será exibida em tela cheia com um fundo escuro para manter a identidade visual.

## Proposed Changes

### [Interface do Usuário]

#### [NEW] [SplashScreen.kt](file:///C:/Users/LenovoGames/AndroidStudioProjects/OuvirNorteMG2/app/src/main/java/britoinfo/ouvirnortemg/ui/SplashScreen.kt)
- Criar um novo componente Composable que exibe a imagem `R.drawable.logo`.
- Utilizar `ContentScale.Crop` ou `ContentScale.Fit` em tela cheia (`fillMaxSize`).
- Adicionar uma animação suave de entrada (Fade In).

#### [MODIFY] [MainActivity.kt](file:///C:/Users/LenovoGames/AndroidStudioProjects/OuvirNorteMG2/app/src/main/java/britoinfo/ouvirnortemg/MainActivity.kt)
- Adicionar lógica de estado para controlar se o Splash deve ser exibido.
- Usar um `Crossfade` para transicionar suavemente da `SplashScreen` para a `RadioListScreen`.

## Verification Plan

### Manual Verification
- Fechar o aplicativo completamente e abri-lo novamente.
- Verificar se a logo aparece grande e em tela cheia por alguns segundos.
- Confirmar se a transição para a lista de rádios ocorre sem erros.

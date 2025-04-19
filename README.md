# intent_pocapp

## 项目目的

本项目 (`intent_pocapp`) 是一个用于学习 Android 安全的 Proof-of-Concept (PoC) 应用程序。其主要目标是通过与目标应用交互来理解和利用 Android 组件（如 Intent、Activity、BroadcastReceiver 等）的潜在安全问题。

## 核心概念

通过编写特定的代码逻辑，`intent_pocapp` 尝试与目标应用 `io.hextree.attacksurface` 进行交互，利用其暴露的组件或漏洞，最终目的是获取预设的 "flag"。

## 目标应用

*   **包名:** `io.hextree.attacksurface`

## 关键组件

*   **Activities:** 包含多个 Activity，用于触发与目标应用的特定交互场景。
*   **BroadcastReceivers:** 实现广播接收器以监听和响应来自系统或其他应用的广播，或向目标应用发送广播。
*   **AndroidManifest.xml:** 配置应用的组件、权限以及与其他应用的交互规则（例如通过 `<queries>` 声明目标应用）。

## 使用方法

1.  **构建:** 使用 Android Studio 或 Gradle 构建此项目。
2.  **安装:** 将 `intent_pocapp` 和目标应用 `io.hextree.attacksurface` 安装到同一 Android 设备或模拟器上。
3.  **运行:** 启动 `intent_pocapp`。
4.  **交互:** 通过 `intent_pocapp` 的界面触发不同的 PoC 场景，尝试与 `io.hextree.attacksurface` 交互。
5.  **观察:** 使用 Logcat 等工具监控应用行为和日志输出，检查是否成功获取 flag。
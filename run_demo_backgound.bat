wt --fullscreen cmd.exe /k "docker logs -f mfc-server-staging"; ^
split-pane -V cmd.exe /k "docker logs -f mfc-cli-staging"; ^
move-focus left; ^
split-pane -H cmd.exe /k "docker logs -f bank-system-staging"; ^
move-focus right; ^
split-pane -H cmd.exe /k "docker logs -f parking-system-staging" ^

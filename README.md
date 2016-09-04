# smart-rename

Smart rename utility.

Before

```bash
n=1; for file in * ; do echo $file `printf "%02d" $n`; ((n++)); done
```

After

```bash
magic
```


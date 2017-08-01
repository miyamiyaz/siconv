# siconv

iconv written in scala

## Abstract
siconv is a yet another implementation of iconv which used java charset encoder/decoder.

## How to install

Download the archive and extract it into proper location. For example:

```
$ unzip siconv-0.0.1.zip
$ mv siconv ~/.local/lib
$ echo 'export PATH=$PATH:$HOME/.local/lib/siconv' >> ~/.bashrc
```

## How to use

Encode from one character encoding to another:

```
$ siconv -f ms932 -t utf8 ./input.txt > output.txt
```

## Compiling from source

### Requirements

- sbt
- java8

### How to compile

Fetch the repository and run `sbt assembly` in the repository location.

```
$ sbt assembly
```


#!/bin/sh

cd $(dirname $0)

mkdir -p stories

for f in ../here2beer-it/assets/features/stories/*.feature ../here2beer-it/forthcoming/*.feature ; do
    sed 's/\(.\{89\}\).*/\1\%*\\ldots*\)/g' $f >stories/$(basename $f)
done

pdflatex proposal.tex

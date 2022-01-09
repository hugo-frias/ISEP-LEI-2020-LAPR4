#!/usr/bin/env bash

#REM set the class path,
#REM assumes the build was executed with maven copy-dependencies
export BASE_CP=base.spm/target/base.spm-1.3.0-SNAPSHOT.jar:base.spm/target/dependency/*;

#REM call the java VM, e.g,
java -cp $BASE_CP eapli.base.spm.ServicoProcessamentoMensagens $*
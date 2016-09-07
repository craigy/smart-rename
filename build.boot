(set-env!
 :source-paths #{"src/clj" "test/clj"}
 :resource-paths #{"resources"}

 :dependencies
 '[[org.clojure/clojure                 "1.9.0-alpha10" :scope "provided"]
   [org.clojure/tools.nrepl             "0.2.12"        :scope "test"]
   [metosin/boot-alt-test               "0.1.2"         :scope "test"]
   [boot/core                           "2.6.0"]
   [org.clojure/tools.cli               "0.3.5"]])

(require '[metosin.boot-alt-test :refer [alt-test]])


(deftask tests
  "Build for development"
  []
  (comp
    (watch)
    (alt-test)))

(deftask prod
  "Build for production"
  []
  (comp
    (aot :all true)
    (pom :project 'smart-rename
         :version "0.0.1-SNAPSHOT")
    (uber)
    (jar :main 'smart-rename.core)
    (target :dir #{"prod"})))


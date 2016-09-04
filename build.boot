(set-env!
 :source-paths #{"src/clj" "test/clj"}
 :resource-paths #{}

 :dependencies
 '[[org.clojure/clojure                 "1.9.0-alpha10" :scope "provided"]
   [org.clojure/clojurescript           "1.9.227"       :scope "provided"]
   [adzerk/boot-cljs                    "1.7.228-1"     :scope "test"]
   [pandeiro/boot-http                  "0.7.3"         :scope "test"]
   [adzerk/boot-reload                  "0.4.12"        :scope "test"]
   [adzerk/boot-cljs-repl               "0.3.3"         :scope "test"]
   [com.cemerick/piggieback             "0.2.1"         :scope "test"]
   [weasel                              "0.7.0"         :scope "test"]
   [org.clojure/tools.nrepl             "0.2.12"        :scope "test"]
   [metosin/boot-alt-test               "0.1.2"         :scope "test"]
   [boot/core                           "2.6.0"]
   [ring/ring-core                      "1.5.0"]
   [ring/ring-jetty-adapter             "1.5.0"]
   [http-kit                            "2.2.0"]
   [compojure                           "1.5.1"]
   [com.taoensso/timbre                 "4.7.4"]])

(require '[adzerk.boot-cljs :refer [cljs]]
         '[pandeiro.boot-http :refer [serve]]
         '[adzerk.boot-reload :refer [reload]]
         '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
         '[metosin.boot-alt-test :refer [alt-test]])


(deftask dev
  "Build for development"
  []
  (comp
    (serve :dir "dev/public"
           :reload true)
    (watch)
    (reload)
    (target :dir #{"dev"})))

(deftask prod
  "Build for production"
  []
  (comp
    (cljs
      :optimizations :advanced)
    (target :dir #{"prod"})))


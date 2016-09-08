(ns smart-rename.core
  (:require
    [clojure.tools.cli :refer [cli]])
  (:import
    [java.io File])
  (:gen-class))


(defn ls [path]
  (seq (.listFiles (File. path))))

(defn num-parts [s]
  (re-seq #"([a-zA-Z]+|\d+)" s))

(defn -main [& args]
  (let [[opts args banner] (cli args
                                ["-h" "--help" "Print this help"
                                 :default false :flag true])]
    (when (:help opts)
      (println banner))))


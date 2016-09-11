(ns smart-rename.core
  (:require
    [clojure.tools.cli :refer [cli]])
  (:import
    [java.io File])
  (:gen-class))


(defn ls [path]
  (seq (.listFiles (File. path))))

(defn parts [s]
  (map
    first
    (re-seq #"([a-zA-Z]+|\d+|[^a-zA-Z\d]+)" s)))

(defn rename [names f i]
  (map
    #(apply
       str
       (map-indexed
         (fn [j n]
           (if (= j i)
             (try
               (f n)
               (catch Exception e
                 n))
             n))
         (parts %)))
    names))

(defn rename-str [names fstr istr]
  (rename
    names
    (eval (read-string fstr))
    (read-string istr)))

(defn -main [& args]
  (let [[opts args banner] (cli args
                                ["-h" "--help" "Print this help"
                                 :default false :flag true])]
    (when (:help opts)
      (println banner))))


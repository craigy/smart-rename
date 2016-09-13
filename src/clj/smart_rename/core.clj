(ns smart-rename.core
  (:require
    [clojure.string :as string]
    [clojure.tools.cli :refer [parse-opts]])
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
    (load-string fstr)
    (read-string istr)))

(defn rename-actions [names f i]
  (let [to (rename names f i)]
    (zipmap names to)))

(defn actions->str [actions noop?]
  (str
    "Actions " (when noop? "to be ") "performed:\n"
    (string/join
      "\n"
      (map
        (fn [[from to]]
          (str from " -> " to (when (= from to) " (unchanged)")))
        actions))))


(def cli-options
  [["-n" "--noop" "Do not perform file operations."
    :default false]
   ["-d" "--directory DIR" "Directory to operate in."
    :default "."]
   ["-p" "--pattern PATTERN" "Pattern of files to include."
    :parse-fn #(re-pattern %)]
   ["-i" "--index NUM" "Index that function operates on."
    :parse-fn #(Integer/parseInt %)]
   ["-f" "--function FN" "Function to run on column with specified index."
    :parse-fn #(load-string %)]
   ["-h" "--help" "Print this help"]])

(defn usage [options-summary]
  (->> ["Smart rename utility."
        ""
        "Usage: smart-rename [options]"
        ""
        "Options:"
        options-summary]
       (string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts
                                                     args
                                                     cli-options)]
    (cond
      (:help options) (exit 0 (usage summary))
      (not= (count arguments) 0) (exit 1 (usage summary))
      errors (exit 1 (error-msg errors)))
    (let [{:keys [noop directory pattern index function]} options
          filenames (map #(.getName %) (ls directory))
          names (filter #(re-find pattern %) filenames)
          actions (rename-actions names function index)]
      ;(println "Success with " options arguments)
      ;(println names)
      (println (actions->str actions noop)))))



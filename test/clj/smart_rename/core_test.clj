(ns smart-rename.core-test
  (:require
    [smart-rename.core :as core]
    [clojure.java.io :as io]
    [clojure.test :refer [deftest is]]))


(deftest test-ls
  (is (= 2
         (count (core/ls (.getPath (io/resource "test"))))))
  (is (= #{"1.txt" "2.txt"}
         (set (map #(.getName %) (core/ls (.getPath (io/resource "test"))))))))

(deftest test-parts
  (is (= ["a" " " "01" " " "b" " " "03" "." "csv"]
         (core/parts "a 01 b 03.csv"))))

(deftest test-rename
  (is (= ["a 2" "b 3" "c 4"]
         (core/rename ["a 1" "b 2" "c 3"] #(inc (bigint %)) 2))))


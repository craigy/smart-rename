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
  (is (= []
         (core/rename [] #(inc (bigint %)) 1)))
  (is (= ["a2" "b3" "c4" "d5"]
         (core/rename ["a1" "b2" "c3" "d4"] #(inc (bigint %)) 1)))
  (is (= ["a 2" "b 3" "c 4"]
         (core/rename ["a 1" "b 2" "c 3"] #(inc (bigint %)) 2)))
  (is (= ["a 2" "b b" "c 4"]
         (core/rename ["a 1" "b b" "c 3"] #(inc (bigint %)) 2)))
  (is (= ["a" "b" "c 2"]
         (core/rename ["a" "b" "c 1"] #(inc (bigint %)) 2)))
  (is (= ["a" "b" "c c 3"]
         (core/rename ["a" "b" "c c 3"] #(inc (bigint %)) 2))))

(deftest test-rename-str
  (is (= ["a 2" "b 3" "c 4"]
         (core/rename-str
           ["a 1" "b 2" "c 3"]
           "#(inc (bigint %))"
           "2"))))

(deftest test-rename-actions
  (is (= {"a 1" "a 2"
          "b 2" "b 3"}
         (core/rename-actions
           ["a 1" "b 2"]
           #(inc (bigint %))
           2))))

(deftest test-actions->str
  (is (= (str
           "Actions performed:\n"
           "a1.txt -> a2.txt")
         (core/actions->str
           {"a1.txt" "a2.txt"}
           false))))


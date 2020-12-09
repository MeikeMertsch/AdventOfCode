(ns christmas.chr16.day20
  (:require [clojure.string :as str]
            [christmas.tools :as tools]))

(defn parse-line[line]
  (->> (re-seq #"\d+" line)
       (map bigint)))

(defn parse-file[file]
  (->> (str/split-lines file)
       (map parse-line)
       (sort-by first))
)
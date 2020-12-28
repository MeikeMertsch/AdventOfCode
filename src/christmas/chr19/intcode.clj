(ns christmas.chr19.intcode
  (:require [clojure.string :as str]
            [christmas.tools :as t]))

(defn parse [string]
  (->> (re-seq #"\d+" string)
       (map t/parse-int)
       (map-indexed vector)
       (apply concat)
       (apply hash-map)
       (hash-map :address 0 :program)))

(defn addresses [address]
  (range (inc address) (+ 4 address)))

(defn simple-op [program [x y z] func]
  (->> (map program [x y]) ;get addresses
       (map program) ;get values
       (apply func)
       (assoc program (program z))))

(defn addition [program numbers]
  (simple-op program numbers +))

(defn multiplication [program numbers]
  (simple-op program numbers *))

(defn end [program _]
  (assoc program :ended true :result (program 0)))

(defn step [{:keys [address program] :as intcode}]
  (-> (case (program address)
        1 addition
        2 multiplication
        99 end)
      (#(update intcode :program % (addresses address)))
      (update :address + 4)))

(defn set-noun-verb [noun verb intcode]
  (update intcode :program assoc 1 noun 2 verb))

(defn to-visible [{:keys [program]}]
  (->> (dissoc program :result :ended)
       (keys)
       (sort)
       (map program)
       (vector :result (:result program) :ended (:ended program false) :code)))

(defn run-program [intcode]
  (->> (iterate step intcode)
       (t/find-first #(:ended (:program %)))
       (:program)
       (:result)))
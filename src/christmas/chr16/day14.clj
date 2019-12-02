(ns christmas.chr16.day14
  (:require [clojure.string :as str]
            [digest :as dig]))

(defn digest [input digestions]
    (->> (iterate dig/md5 input)
         (drop digestions)
         first))

(defn contains-triple?[code]
				(->> (partition 3 1 code)
									(filter (partial apply =))
									ffirst))

(defn contains-5tuple?[code]
				(->> (partition 5 1 code)
									(filter (partial apply =))
									(map first)))

(defn process-tuple[number {:keys [pad-keys candidates] :as knowledge} tuple]
				(let [happy-candidates (map first (filter (comp #{tuple} last) candidates))]
  		    (-> (update-in knowledge [:pad-keys] #(take 64 (sort (concat % happy-candidates))))
  		        (update-in [:candidates] #(apply dissoc % happy-candidates)))))


(defn process-next[{:keys [candidates hash digestions] :as knowledge} number]
	   (let [code (digest (str hash number) digestions)
	   						triple (contains-triple? code)
	         tuples (contains-5tuple? code)]
	       (-> (if (empty? tuples) knowledge (reduce (partial process-tuple number) knowledge tuples))
	           (#(if triple (assoc-in % [:candidates number] triple) %))
	   	       (update-in [:candidates] #(dissoc % (- number 1001))))))

(defn process[input digestions]
				(->> (reductions process-next {:pad-keys [] :candidates {} :hash input :digestions digestions} (range))
				     (drop-while #(or (< (count (:pad-keys %)) 64) 
				     			              (< (first (sort (keys (:candidates %)))) (last (:pad-keys %)))))
				     first
				     :pad-keys
				     last))

